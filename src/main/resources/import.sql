
/*INSERT de tabla de  tipos de gastos*/

INSERT INTO tipo_gastos (tipo) VALUES ('GASTO_COMUN');
INSERT INTO tipo_gastos (tipo) VALUES  ('FERRETERIA');
INSERT INTO tipo_gastos (tipo) VALUES ('SUPERMERCADO');
INSERT INTO tipo_gastos (tipo) VALUES  ('VIAJE');
INSERT INTO tipo_gastos (tipo) VALUES ('ALOJAMIENTO');
INSERT INTO tipo_gastos (tipo) VALUES  ('LEÑA');
INSERT INTO tipo_gastos (tipo) VALUES ('CASA_COMERCIAL');

/* INSERT de tabla de gasto*/
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto_id) VALUES  ('Se compro en laja en el supermercado unico carne,aceite y salmon para el almuerzo','2024-02-14',3);/*RETURNING id, invoice_date;*/
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto_id) VALUES  ('Se compro dos pasajes para santiago de ida','2024-03-20',4);
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto_id) VALUES ('se compraron en la ferreteria gran fe: destornillador,3 metros de malla y 6 sacos para basura','2023-12-06',2);
INSERT INTO gastos (descripcion,fecha_gasto,tipo_gasto_id) VALUES ('Se compro 3 metros de leña','2024-03-20',6);

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

/* INSERT  de tabla local_pagos*/
INSERT INTO local_pagos (nombre,direccion,ciudad) VALUES ('Supermercado Unico','Balmaceda #456','Laja');
INSERT INTO local_pagos (nombre,direccion,ciudad) VALUES ('Ferreteria Gran fe','Ohiggins #528','Laja');
INSERT INTO local_pagos (nombre,direccion,ciudad) VALUES ('no','no','Laja');
INSERT INTO local_pagos (nombre,direccion,ciudad) VALUES ('terminal buses laja','balmaceda #120','Laja');

/* INSERT de tabla pagos*/
INSERT INTO pagos (tipo_pago,metodo_online,gasto_id,local_pago_id) VALUES ('DEBITO',DEFAULT,1,1);
INSERT INTO pagos (tipo_pago,metodo_online,gasto_id,local_pago_id) VALUES  ('DEBITO',DEFAULT,2,1);


/* INSERT de tabla notas*/
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('Viaje a Santiago','se debe viajar el 25 de marzo a desbloquear el codificador','2024-03-07');
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('llegada de tv','se compro un tv por la pagina de falabella era de 60 pulgadas marca samsung','2024-03-04');
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('Ida a dipreca','se fue a pedir que a mi mama le depositen la pension en vez que vaya al banco','2023-02-06');
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('comprar leña','debido a que se acerca el imvierno se necesita comprar leña','2024-03-02');
INSERT INTO notas (titulo,contenido,fecha_nota) VALUES ('poner malla a cerco de fierro','se compro 3 metros de malla para reforzar cerco de fierro','2023-10-17');