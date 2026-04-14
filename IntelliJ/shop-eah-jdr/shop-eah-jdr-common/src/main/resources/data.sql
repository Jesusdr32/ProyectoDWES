--CATEGORIAS
INSERT INTO category (name, description, image) VALUES
                        ('Acción', 'Videojuegos centrados en el combate, los reflejos y el dinamismo.', '/images/categoriaAccion.png'),
                        ('Aventura', 'Centrados en la exploración y la historia, con mundos inmersivos y narrativas épicas.', '/images/categoriaAventura.png'),
                        ('Deportes', 'Juegos basados en disciplinas deportivas como fútbol, baloncesto, ...', '/images/categoriaDeportes.png'),
                        ('Estrategia', 'Títulos que requieren planificación táctica y toma de decisiones.', '/images/categoriaEstrategia.png'),
                        ('RPG', 'Role Playing Games donde el jugador progresa y desarrolla habilidades.', '/images/categoriaRPG.png'),
                        ('Carreras', 'Videojuegos centrados en competencias de velocidad con vehículos.', '/images/categoriaCarreras.png'),
                        ('Simulación', 'Juegos que recrean experiencias de la vida real o actividades especializadas.', '/images/categoriaSimulacion.png'),
                        ('Battle Royale', 'Juegos de supervivencia donde muchos jugadores compiten hasta quedar uno.', '/images/categoriaBattleRoyale.png'),
                        ('Lucha', 'Ofrecen combates intensos entre personajes con habilidades únicas.', NULL);

--MARCAS
INSERT INTO brand (name, description, image) VALUES
                    ('Nintendo','Desarrolladora y editora japonesa creadora de sagas icónicas como Mario, Zelda y Pokémon.','/images/brands/nintendo.png'),
                    ('Rockstar Games','Estudio estadounidense conocido por desarrollar juegos de mundo abierto como GTA y Red Dead Redemption.','/images/brands/rockstar.png'),
                    ('CD Projekt Red','Desarrolladora polaca reconocida por The Witcher y Cyberpunk 2077.','/images/brands/cdprojektred.png'),
                    ('Ubisoft','Compañía francesa desarrolladora de franquicias como Assassin’s Creed.','/images/brands/ubisoft.png'),
                    ('FromSoftware','Estudio japonés famoso por la saga Dark Souls y Elden Ring.','/images/brands/fromsoftware.png'),
                    ('Naughty Dog','Estudio de Sony responsable de The Last of Us y Uncharted.','/images/brands/naughtydog.png'),
                    ('Santa Monica Studio','Estudio responsable de la aclamada saga God of War y referentes en acción narrativa.','/images/brands/santamonicastudio.png'),
                    ('Bethesda Game Studios','Desarrolladora de RPGs como Skyrim y Fallout.','/images/brands/bethesda.png'),
                    ('Valve','Desarrolladora estadounidense creadora de sagas como Half-Life, Portal y Left 4 Dead.',NULL);;


-- PRODUCTOS
INSERT INTO product (name, description, brand_id, image, ean, price, discount, stock) VALUES
                    ('Shadow Strike', 'Juego de acción trepidante.', 1, '/images/products/shadowStrike.png', '4006381333931', 59.99, 10, 50),
                    ('Adventure Quest', 'Aventura épica llena de misterios.', 3, NULL, '4006381333948', 49.99, 0, 30),
                    ('Ultimate Soccer 2026', 'Simulación de fútbol realista.', 5, '/images/products/ultimateSoccer2026.png', '4006381333955', 69.99, 15, 40),
                    ('Castle Strategist', 'Estrategia medieval avanzada.', 2, '/images/products/castleStrategist.png', '4006381333962', 39.99, 5, 25),
                    ('Fantasy RPG Legends', 'RPG de fantasía profunda.', 4, NULL, '4006381333979', 59.99, 20, 60),
                    ('Speed Racers', 'Carreras de alta velocidad.', 6, '/images/products/speedRacers.png', '4006381333986', 44.99, 0, 35),
                    ('SimLife Tycoon', 'Simulador de gestión de ciudades.', 2, NULL, '4006381333993', 49.99, 10, 20),
                    ('Battle Royale Extreme', 'Supervivencia multijugador.', 5, '/images/products/battleRoyaleExtreme.png', '4006381334006', 54.99, 0, 45),
                    ('Ultimate Fighters', 'Combates intensos.', 1, '/images/products/ultimateFighters.png', '4006381334013', 59.99, 5, 50),
                    ('Action Hero 2', 'Acción explosiva en mundo abierto.', 3, NULL, '4006381334020', 49.99, 0, 30),
                    ('Mystery Island', 'Aventura y puzzles en una isla.', 4, '/images/products/mysteryIsland.png', '4006381334037', 54.99, 5, 20),
                    ('Pro Basketball 2026', 'Baloncesto profesional.', 6, NULL, '4006381334044', 64.99, 10, 25),
                    ('War Tactics', 'Estrategia bélica en tiempo real.', 2, '/images/products/warTactics.png', '4006381334051', 39.99, 0, 15),
                    ('Dragon Realms', 'RPG con dragones y magia.', 1, '/images/products/dragonRealms.png', '4006381334068', 59.99, 15, 40),
                    ('Formula Extreme', 'Carreras de fórmula.', 3, NULL, '4006381334075', 49.99, 0, 30),
                    ('City Builder Deluxe', 'Construcción y gestión urbana.', 6, '/images/products/cityBuilderDeluxe.png', '4006381334082', 44.99, 5, 20),
                    ('Survival Arena', 'Batalla por la supervivencia.', 5, NULL, '4006381334099', 54.99, 10, 35),
                    ('Fight Masters', 'Lucha competitiva.', 4, '/images/products/fightMasters.png', '4006381334105', 59.99, 0, 25),
                    ('Action Legends', 'Misiones de acción épicas.', 1, '/images/products/actionLegends.png', '4006381334112', 49.99, 5, 30),
                    ('Adventure World', 'Embárcate en Adventure World, un juego de exploración y aventura donde recorrerás paisajes coloridos llenos de secretos, desafíos y personajes únicos. Viaja por bosques encantados, montañas nevadas y antiguas ruinas mientras resuelves acertijos, completas misiones secundarias y recolectas objetos especiales que te ayudarán a avanzar. A medida que progresas, podrás mejorar habilidades, desbloquear nuevas áreas y descubrir historias ocultas del mundo que te rodea, ofreciendo una experiencia relajante y entretenida para todo tipo de jugadores.', 2, NULL, '4006381334129', 44.99, 0, 50);


-- RELACIÓN PRODUCTOS - CATEGORÍAS
INSERT INTO product_category (product_id, category_id) VALUES
-- Shadow Strike → Acción
(1, 1),
-- Adventure Quest → Aventura, RPG
(2, 2),
(2, 5),
-- Ultimate Soccer 2026 → Deportes
(3, 3),
-- Castle Strategist → Estrategia
(4, 4),
-- Fantasy RPG Legends → RPG, Aventura
(5, 5),
(5, 2),
-- Speed Racers → Carreras
(6, 6),
-- SimLife Tycoon → Simulación, Estrategia
(7, 7),
(7, 4),
-- Battle Royale Extreme → Battle Royale, Acción
(8, 8),
(8, 1),
-- Ultimate Fighters → Lucha, Acción
(9, 9),
(9, 1),
-- Mystery Island → Aventura
(11, 2),
-- Pro Basketball 2026 → Deportes
(12, 3),
-- War Tactics → Estrategia
(13, 4),
-- Dragon Realms → RPG, Acción
(14, 5),
(14, 1),
-- Formula Extreme → Carreras, Deportes
(15, 6),
(15, 3),
-- City Builder Deluxe → Simulación
(16, 7),
-- Survival Arena → Battle Royale
(17, 8),
-- Fight Masters → Lucha
(18, 9),
-- Action Legends → Acción, Aventura
(19, 1),
(19, 2);


-- INSERT del punto 3.5 para crear un usuario inicial
INSERT INTO userapp
(username, first_name, last_name, email, password, registration_date)
VALUES
    ('admin',
     'Admin',
     'Admin',
     'admin@tienda.com',
     '$2a$12$SCr8t0pZYisI7OcNUaHlM.rR4tgjCQujP5XHznhP2p5s3HInW0yom',
     CURRENT_TIMESTAMP),
    ('user',
     'User',
     'User',
     'user@tienda.com',
     '$2a$12$SCr8t0pZYisI7OcNUaHlM.rR4tgjCQujP5XHznhP2p5s3HInW0yom',
     CURRENT_TIMESTAMP);

-- INSERT del 3.3 para crear dos roles en el sistema
INSERT INTO roles
(rol_id, rol_description)
VALUES
    ('USER', 'Usuario normal'),
    ('ADMIN', 'Administrador');

-- INSERT del 3.3 para asignar al usuario admin el rol ADMIN
INSERT INTO user_roles
(user_id, rol_id)
VALUES
    ((SELECT user_id FROM userapp WHERE email = 'admin@tienda.com'), 'ADMIN'),
    ((SELECT user_id FROM userapp WHERE email = 'admin@tienda.com'), 'USER'),
    ((SELECT user_id FROM userapp WHERE email = 'user@tienda.com'), 'USER');