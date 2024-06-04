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
	("Facundo", "2024-05-02", 3, 10)


