package ch.marcbuchser.ristores;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.search.mapper.orm.Search;
import org.jboss.resteasy.annotations.jaxrs.FormParam;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import ch.marcbuchser.ristores.model.Guest;
import ch.marcbuchser.ristores.model.Menu;
import io.quarkus.runtime.StartupEvent;

@Path("/ristores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RistoresResource {

    @Inject
    EntityManager em;

    @Transactional
    void onStart(@Observes StartupEvent ev) throws InterruptedException {
        // only reindex if we imported some content
        if (Guest.count() > 0) {
            Search.session(em).massIndexer().startAndWait();
        }
    }

    @PUT
    @Path("guest")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addGuest(@FormParam String guestName, @FormParam Long menuId) {
        Menu menu = Menu.findById(menuId);
        if (menu == null) {
            return;
        }

        Guest guest = new Guest();
        guest.guestName = guestName;
        guest.menu = menu;
        guest.persist();

        menu.guests.add(guest);
        menu.persist();
    }

    @DELETE
    @Path("guest/{id}")
    @Transactional
    public void deleteGuest(@PathParam Long id) {
        Guest guest = Guest.findById(id);
        if (guest != null) {
            guest.menu.guests.remove(guest);
            guest.delete();
        }
    }

    @PUT
    @Path("menu")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addMenu(@FormParam String menuTitle, @FormParam String menuContent, @FormParam String menuDate) {
        Menu menu = new Menu();
        menu.menuTitle = menuTitle;
        menu.menuContent = menuContent;
        menu.menuDate = menuDate;
        menu.persist();
    }

    @POST
    @Path("menu/{id}")
    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateMenu(@PathParam Long id, @FormParam String menuTitle, @FormParam String menuContent, @FormParam String menuDate) {
        Menu menu = Menu.findById(id);
        if (menu == null) {
            return;
        }
        menu.menuTitle = menuTitle;
        menu.menuContent = menuContent;
        menu.menuDate = menuDate;
        menu.persist();
    }

    @DELETE
    @Path("menu/{id}")
    @Transactional
    public void deleteMenu(@PathParam Long id) {
        Menu menu = Menu.findById(id);
        if (menu != null) {
            menu.delete();
        }
    }

    @GET
    @Path("menu/search")
    @Transactional
    public List<Menu> searchMenus(@QueryParam String pattern,
            @QueryParam Optional<Integer> size) {
        List<Menu> menus = Search.session(em)
                .search(Menu.class)
                .where(f -> pattern == null || pattern.trim().isEmpty() ? f.matchAll()
                        : f.simpleQueryString()
                                .fields("menuTitle", "menuContent", "menuDate", "guests.guestName").matching(pattern))
                .sort(f -> f.field("menuDate_sort").then().field("menuTitle_sort").then().field("menuContent_sort"))
                .fetchHits(size.orElse(20));
        return menus;
    }

    @GET
    @Path("guest/search")
    @Transactional
    public List<Guest> searchGuests(@QueryParam String pattern,
            @QueryParam Optional<Integer> size) {
        List<Guest> guests = Search.session(em)
                .search(Guest.class)
                .where(f -> pattern == null || pattern.trim().isEmpty() ? f.matchAll()
                        : f.simpleQueryString()
                                .fields("guestName").matching(pattern))
                .sort(f -> f.field("guestName_sort"))
                .fetchHits(size.orElse(20));
        return guests;
    }
}
