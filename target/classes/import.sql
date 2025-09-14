


/* INSERT de tabla de gasto*/
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto) VALUES  ('Se compro en laja en el supermercado unico carne,aceite y salmon para el almuerzo','2024-02-14','SUPERMERCADO');/*RETURNING id, invoice_date;*/
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto) VALUES  ('Se compro dos pasajes para santiago de ida','2024-03-20','VIAJE');
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto) VALUES ('se compraron en la ferreteria gran fe: destornillador,3 metros de malla y 6 sacos para basura','2023-12-06','FERRETERIA');
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto) VALUES ('Se compro 3 metros de leña','2024-03-20','LEÑA');
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto) VALUES ('Se compro 3 metros de leña','2024-03-20','LEÑA');
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto) VALUES ('Se compro 3 metros de leña','2024-03-20','LEÑA');

/* INSERT de tabla detalle_gastos */

INSERT INTO detalle_gastos (producto,cantidad,precio_unitario,gasto_id) VALUES  ('aceite',1 ,4580,1);
INSERT INTO detalle_gastos (producto,cantidad,precio_unitario,gasto_id) VALUES  ('salmon',1,11000,1);
INSERT INTO detalle_gastos (producto,cantidad,precio_unitario,gasto_id) VALUES  ('costilla de pavo',3,2990,1);
INSERT INTO detalle_gastos (producto,cantidad,precio_unitario,gasto_id) VALUES ('longaniza de chillan',4,7980,1);

INSERT INTO detalle_gastos (producto,cantidad,precio_unitario,gasto_id) VALUES ('pasaje a santiago',2, 23000,2);
INSERT INTO detalle_gastos (producto,cantidad,precio_unitario,gasto_id) VALUES  ('destornillador',1,4000,3);
INSERT INTO detalle_gastos (producto,cantidad,precio_unitario,gasto_id) VALUES ('malla para reja de fierro',3,45000,3);
INSERT INTO detalle_gastos (producto,cantidad,precio_unitario,gasto_id) VALUES ('saco de escombros',6,500,3);
INSERT INTO detalle_gastos (producto,cantidad,precio_unitario,gasto_id) VALUES ('leña',3,20000,4);


/* INSERT de tabla notas*/
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('Viaje a Santiago','se debe viajar el 25 de marzo a desbloquear el codificador','2024-03-07');
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('llegada de tv','se compro un tv por la pagina de falabella era de 60 pulgadas marca samsung','2024-03-04');
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('Ida a dipreca','se fue a pedir que a mi mama le depositen la pension en vez que vaya al banco','2023-02-06');
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('comprar leña','debido a que se acerca el imvierno se necesita comprar leña','2024-03-02');
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('poner malla a cerco de fierro','se compro 3 metros de malla para reforzar cerco de fierro','2023-10-17');