CREATE DATABASE springecomdb;

-- This table will store your user information.
-- NOTE: You don't have to run this manually.
-- Spring Boot/Hibernate will create it for you on startup.
CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `full_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `phone_number` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);

SELECT * FROM springecomdb.users;

CREATE TABLE `products` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `price` DECIMAL(10, 2) NOT NULL,
  `category` VARCHAR(50) NOT NULL,
  `image_url` VARCHAR(255),
  `prep_time_in_minutes` INT,
  `rating` DOUBLE,
  `is_vegan` BOOLEAN,
  PRIMARY KEY (`id`)
);

INSERT INTO `products` (name, description, price, category, image_url, prep_time_in_minutes, rating, is_vegan) VALUES
('Mediterranean Quinoa Bowl', 'Fresh quinoa with roasted vegetables, olives, and tahini dressing. A perfect vegan meal for a healthy and satisfying lunch or dinner.', 12.99, 'BOWL', 'mediterannean-quinoa-salad-5.jpg', 15, 4.8, true),
('Green Goddess Salad', 'Mixed greens with avocado, cucumber, and a creamy green goddess dressing. Light, refreshing, and full of healthy fats.', 10.99, 'SALAD', 'healthy-green-goddess-salad-3.jpg', 10, 4.9, true),
('Tropical Smoothie Bowl', 'A vibrant and refreshing smoothie bowl with mango and pineapple, topped with shredded coconut and crunchy granola.', 9.99, 'SMOOTHIE', 'tropical-smoothie-bowl.jpeg', 8, 4.7, true),
('Protein Power Wrap', 'A hearty whole wheat wrap filled with grilled chicken, crisp lettuce, tomatoes, and a savory sauce. Ideal for a quick, protein-packed meal.', 11.99, 'WRAP', 'protein-power-wrap.jpg', 12, 4.6, false),
('Buddha Bowl', 'A colorful Buddha bowl featuring roasted sweet potatoes, broccoli, chickpeas, and a creamy avocado dressing over a bed of brown rice.', 13.99, 'BOWL', 'buddha-bowl.jpg', 18, 4.9, true),
('Berry Antioxidant Smoothie', 'A refreshing blend of blueberries, strawberries, and açaí, packed with antioxidants. A great way to start your day or refuel after a workout.', 8.99, 'SMOOTHIE', 'berry-antioxidant-smoothie.jpeg', 5, 4.8, true);

-- Creates the main shopping cart table. Each user will have one cart.
CREATE TABLE `carts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
);

-- Creates the table to hold individual items within a cart.
CREATE TABLE `cart_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `cart_id` BIGINT NOT NULL,
  `product_id` BIGINT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`cart_id`) REFERENCES `carts`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`product_id`) REFERENCES `products`(`id`) ON DELETE CASCADE
);

ALTER TABLE cart_items
ADD COLUMN customizations TEXT;

CREATE TABLE `orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT,
  `order_date` DATETIME,
  `total_amount` DECIMAL(10, 2),
  `delivery_address` TEXT,
  `status` VARCHAR(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);

CREATE TABLE `order_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT,
  `product_id` BIGINT,
  `quantity` INT,
  `price` DECIMAL(10, 2),
  `customizations` TEXT,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`),
  FOREIGN KEY (`product_id`) REFERENCES `products`(`id`)
);



