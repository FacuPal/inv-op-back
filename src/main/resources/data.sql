/*CREATE DATABASE db_inv_op;*/
use db_inv_op;

/*Parameters*/
INSERT INTO parameter (parameter_name, parameter_value, is_deleted)
VALUES
("PERIODOS_A_PREDECIR", "3", 0),
("METODO_CALCULO_ERROR", "MAD", 0),
("ERROR_ACEPTABLE", "0.5", 0);

/*Suppliers*/
INSERT INTO supplier (supplier_name, supplier_delivery_time, is_deleted) VALUES ("Supplier 1", 15, 0);
INSERT INTO supplier (supplier_name, supplier_delivery_time, is_deleted) VALUES ("Supplier 2", 2, 0);
INSERT INTO supplier (supplier_name, supplier_delivery_time, is_deleted) VALUES ("Supplier 3", 7, 0);

/*Inventory Model*/
INSERT INTO inventory_model (inventory_model_name,is_deleted) VALUES ("Intervalo Fijo", 0);
INSERT INTO inventory_model (inventory_model_name,is_deleted) VALUES ("Lote Fijo", 0);


/*Product Family*/

INSERT INTO product_family (product_family_name, is_deleted, supplier_id, inventory_model_id) VALUES ("Family1", 0, 1, 1),("Family2", 0, 2, 2),("Family3",0,3,1);

/*Product*/
INSERT INTO product (product_name ,product_family_id ,stock,safe_stock,order_limit,optimal_batch) 
values 
	("Articulo 1",1, 3,2,4,10),
	("Articulo 2", 2, 4,4,5,15),
	("Articulo 3", 2, 10,6,8,20),
	("Articulo 4", 2, 0,5,3,4),
	("Articulo 5", 1, 15,10,12,50),
	("Articulo 6", 3,8,5,8,10);

/*Sale*/
INSERT INTO sale (customer_name, sale_date, product_id, quantity)
VALUES 
	("Facundo", "2024-06-02", 1, 10),
	("Facundo", "2024-06-01", 1, 10),
	("Facundo", "2024-05-31", 1, 10),
	("Facundo", "2024-05-30", 1, 10),
	("Facundo", "2024-05-02", 3, 10);

/*PurchaseOrderStatus*/
INSERT INTO purchase_order(product_id, purchase_order_date, supplier_id, purchase_order_status, order_quantity) 
VALUES 
	(1, '2024-06-09', 1, 'OPEN',5),
	(1, '2024-06-09', 1, 'OPEN',10),
	(1, '2024-06-09', 2, 'OPEN',8);

/*Historic Demand*/
INSERT INTO `historic_demand` (`month`, `quantity`, `year`, `historic_demand_id`, `product_id`) VALUES
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
(12, 23, 2023, 18, 2),
(1, 8, 2023, 19, 3),
(2, 7, 2023, 20, 3),
(3, 5, 2023, 21, 3),
(4, 7, 2023, 22, 3),
(5, 9, 2023, 23, 3),
(6, 7, 2023, 24, 3),
(7, 6, 2023, 25, 3),
(8, 8, 2023, 26, 3),
(9, 9, 2023, 27, 3),
(10, 8, 2023, 28, 3),
(11, 6, 2023, 29, 3),
(12, 7, 2023, 30, 3),
(1, 9, 2024, 31, 3),
(2, 7, 2024, 32, 3),
(3, 6, 2024, 33, 3),
(4, 8, 2024, 34, 3),
(5, 9, 2024, 35, 3),
(6, 7, 2024, 36, 3);

/*Demand Prediction Model Type*/
INSERT INTO `demand_prediction_model_type` (`is_deleted`, `demand_prediction_model_type_id`, `demand_prediction_model_type_name`) VALUES
(b'0', 1, 'PMP'),
(b'0', 2, 'PMSE'),
(b'0', 3, 'RL'),
(b'0', 4, 'Ix');

/*Demand Prediction Model*/
INSERT INTO `demand_prediction_model` (`alpha`, `expected_demand`, `ignore_periods`, `is_deleted`, `length`, `root`, `demand_prediction_model_id`, `demand_prediction_model_product_family_id`, `demand_prediction_model_product_id`, `demand_prediction_model_type_id`, `demand_prediction_model_color`, `dtype`, `pmp_demand_prediction_model_ponderations`) VALUES
(NULL, NULL, NULL, b'0', NULL, NULL, 1, NULL, 2, 1, '#0000FF', 'PMP', '1;2;3'),
(NULL, NULL, NULL, b'0', NULL, NULL, 2, NULL, 2, 1, '#00ffff', 'PMP', '1;1;1'),
(0.9, NULL, NULL, b'0', NULL, 9, 3, NULL, 2, 2, '#ffa500', 'PMSE', NULL),
(0.1, NULL, NULL, b'0', NULL, 11, 4, NULL, 2, 2, '#ff0000', 'PMSE', NULL),
(NULL, NULL, 0, b'0', NULL, NULL, 5, NULL, 2, 3, '#008000', 'RL', NULL),
(NULL, NULL, 2, b'0', NULL, NULL, 6, NULL, 2, 3, '#006400', 'RL', NULL),
(NULL, 70, NULL, b'0', 3, NULL, 7, NULL, 2, 4, '#ff7f50', 'Ix', NULL),
(NULL, 30, NULL, b'0', 4, NULL, 8, NULL, 3, 4, '#800080', 'Ix', NULL),
(NULL, NULL, 0, b'0', NULL, NULL, 9, NULL, 3, 3, '#ff0000', 'RL', NULL),
(NULL, NULL, NULL, b'0', NULL, NULL, 10, NULL, 3, 1, '#008000', 'PMP', '1;1;1;1'),
(0.9, NULL, NULL, b'0', NULL, 7.5, 11, NULL, 3, 2, '#0000FF', 'PMSE', NULL);