/*CREATE DATABASE db_inv_op;*/
use db_inv_op;

/*Suppliers*/
INSERT INTO supplier (supplier_name, supplier_delivery_time, is_deleted) VALUES ("Supplier 1", 15, 0);
INSERT INTO supplier (supplier_name, supplier_delivery_time, is_deleted) VALUES ("Supplier 2", 2, 0);
INSERT INTO supplier (supplier_name, supplier_delivery_time, is_deleted) VALUES ("Supplier 3", 7, 0);

/*Inventory Model*/
INSERT INTO inventory_model (inventory_model_name,is_deleted) VALUES ("Intervalo Fijo", 0);
INSERT INTO inventory_model (inventory_model_name,is_deleted) VALUES ("Lote Fijo", 0);


/*Product Family*/

INSERT INTO product_family (product_family_name, is_deleted, supplier_id, inventory_model_id) VALUES ("Family1", 0, 1, 1),("Family2", 0, 1, 1);

/*Product*/
INSERT INTO product (product_name ,product_family_id ,stock) 
values 
	("Articulo 1",1, 1),
	("Articulo 2", 2, 3),
	("Articulo 3", 2, 3),
	("Articulo 4", 2, 5),
	("Articulo 5", 1, 6);

/*Sale*/
INSERT INTO sale (customer_name, sale_date, product_id, quantity)
VALUES 
	("Facundo", "2024-06-02", 1, 10),
	("Facundo", "2024-06-01", 1, 10),
	("Facundo", "2024-05-31", 1, 10),
	("Facundo", "2024-05-30", 1, 10),
	("Facundo", "2024-05-02", 3, 10);

/*Historic Demand*/
INSERT INTO historic_demand (month, quantity, year, historic_demand_id, product_id) VALUES
(1, 1, 2024, 1, 1),
(2, 2, 2024, 2, 1),
(3, 3, 2024, 3, 1),
(4, 4, 2024, 4, 1),
(5, 5, 2024, 5, 1),
(6, 4, 2024, 6, 1),
(1, 26, 2024, 7, 2),
(2, 30, 2024, 8, 2),
(3, 28, 2024, 9, 2),
(4, 18, 2024, 10, 2),
(5, 16, 2024, 11, 2),
(6, 14, 2024, 12, 2),
(7, 10, 2023, 13, 2),
(8, 12, 2023, 14, 2),
(9, 13, 2023, 15, 2),
(10, 16, 2023, 16, 2),
(11, 19, 2023, 17, 2),
(12, 23, 2023, 18, 2);
