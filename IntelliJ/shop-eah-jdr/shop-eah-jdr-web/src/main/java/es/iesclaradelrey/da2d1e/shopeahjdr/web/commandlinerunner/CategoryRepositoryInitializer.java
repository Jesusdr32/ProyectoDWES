package es.iesclaradelrey.da2d1e.shopeahjdr.web.commandlinerunner;

import es.iesclaradelrey.da2d1e.shopeahjdr.common.entities.Category;
import es.iesclaradelrey.da2d1e.shopeahjdr.common.services.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryRepositoryInitializer implements CommandLineRunner {
    private static final int CATEGORY_COUNT = 4;
    private final CategoryService categoryService;

    public CategoryRepositoryInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
       Category accion = new Category();
       accion.setName("Acción");
       accion.setDescription("Videojuegos centrados en el combate, los reflejos y el dinamismo.");
       accion.setImage("/images/categoriaAccion.png");
       categoryService.save(accion);

       Category aventura = new Category();
       aventura.setName("Aventura");
       aventura.setDescription("Videojuegos centrados en la exploración, la historia y la resolución de puzzles, con mundos inmersivos y narrativas épicas.");
       aventura.setImage("/images/categoriaAventura.png");
       categoryService.save(aventura);

       Category deportes = new Category();
       deportes.setName("Deportes");
       deportes.setDescription("Juegos basados en disciplinas deportivas como fútbol, baloncesto, ...");
       deportes.setImage("/images/categoriaDeportes.png");
       categoryService.save(deportes);

       Category estrategia = new Category();
       estrategia.setName("Estrategia");
       estrategia.setDescription("Títulos que requieren planificación táctica y toma de decisiones.");
       estrategia.setImage("/images/categoriaEstrategia.png");
        categoryService.save(estrategia);

       Category rpg = new Category();
       rpg.setName("RPG");
       rpg.setDescription("Role Playing Games donde el jugador progresa y desarrolla habilidades.");
       rpg.setImage("/images/categoriaRPG.png");
        categoryService.save(rpg);

       Category carreras = new Category();
       carreras.setName("Carreras");
       carreras.setDescription("Videojuegos centrados en competencias de velocidad con vehículos.");
       carreras.setImage("/images/categoriaCarreras.png");
       categoryService.save(carreras);

       Category simulacion = new Category();
       simulacion.setName("Simulación");
       simulacion.setDescription("Juegos que recrean experiencias de la vida real o actividades especializadas.");
       simulacion.setImage("/images/categoriaSimulacion.png");
       categoryService.save(simulacion);

       Category battleRoyale = new Category();
       battleRoyale.setName("Battle Royale");
       battleRoyale.setDescription("Multijugador de supervivencia donde muchos jugadores compiten hasta quedar uno.");
       battleRoyale.setImage("/images/categoriaBattleRoyale.png");
       categoryService.save(battleRoyale);

       Category lucha = new Category();
       lucha.setName("Lucha");
       lucha.setDescription("Ofrecen combates intensos entre personajes con habilidades únicas, ideal para quienes disfrutan de la acción rápida y estratégica.");
       //lucha.setImage("/images/categoriaLucha.png");
        categoryService.save(lucha);
    }
}
