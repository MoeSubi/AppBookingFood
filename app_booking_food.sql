-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th7 30, 2023 lúc 08:23 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `app_booking_food`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `id_cate` int(11) NOT NULL,
  `category_name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`id_cate`, `category_name`) VALUES
(2, 'Ưu Đãi'),
(3, 'Món Mới'),
(4, 'Combo 1 Người'),
(5, 'Combo Nhóm'),
(6, 'Burger - Cơm - Mì Ý'),
(7, 'Thức Uống & Tráng Miệng'),
(8, 'Đơn Hàng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `food_detail`
--

CREATE TABLE `food_detail` (
  `id_detail` int(11) NOT NULL,
  `food_name` varchar(250) NOT NULL,
  `price` varchar(100) NOT NULL,
  `image_food` text NOT NULL,
  `description` text NOT NULL,
  `id_cate` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `food_detail`
--

INSERT INTO `food_detail` (`id_detail`, `food_name`, `price`, `image_food`, `description`, `id_cate`) VALUES
(1, 'Combo Gà Rán\r\n', '89000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/D1-new.jpg?v=gqOojL', '2 Miếng Gà +1 Khoai tây chiên vừa / 2 Gà Miếng Nuggets + 1 Lipton vừa', 3),
(2, 'Combo Summer 159k\r\n', '159000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/CBO159K_Summer.jpg?v=gqOojL', '3 Miếng Gà + 1 Gà Viên (Vừa)/ 1 Burger Tôm + 2 lon Pepsi + 2 Phiếu Cào', 2),
(3, '2 Bánh Khoai Tây Chiên\r\n', '17000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/2-Hash-Browns.jpg?v=gqOojL', '2 Bánh Khoai Tây Chiên', 3),
(4, 'Combo Nhóm 2\r\n', '195000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/D7-new.jpg?v=gqOojL', '4 Miếng Gà + 1 Khoai tây chiên lớn / 2 Thanh Bí Phô-mai + 2 Pepsi Lon', 5),
(5, 'Burger Gà Quay Flava\r\n', '55000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/Burger-Flava.jpg?v=gqOojL', '1 Burger Gà Quay Flava', 6),
(6, 'Cơm Gà Teriyaki\r\n', '46000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-Teriyaki.jpg?v=gqOojL', '1 Cơm Gà Teriyaki', 6),
(7, 'Mì Ý Xốt Cà Xúc Xích Gà Viên\r\n', '41000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/MY-Y-POP.jpg?v=gqOojL', '1 Mì Ý Xốt Cà Xúc Xích Gà Viên', 6),
(8, '4 Bánh Trứng\r\n', '59000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/4-eggtart.jpg?v=gqOojL', '4 Bánh Trứng', 7),
(9, 'Pepsi Lon\r\n', '17000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/Pepsi-Can.jpg?v=gqOojL', 'Pepsi Lon', 7),
(10, 'Trà Chanh Lipton (Vừa)\r\n', '10000\r\n', 'https://static.kfcvietnam.com.vn/images/items/lg/Lipton.jpg?v=gqOojL', 'Trà Chanh Lipton (Vừa)', 7),
(11, '1 Bánh Trứng', '18000', 'https://static.kfcvietnam.com.vn/images/items/lg/1-eggtart.jpg?v=gqOojL', '1 Bánh Trứng', 7),
(12, '2 Viên Khoai Môn Kim Sa', '26000', 'https://static.kfcvietnam.com.vn/images/items/lg/2-taro.jpg?v=gqOojL', '2 Viên Khoai Môn Kim Sa', 7),
(13, '3 Viên Khoai Môn Kim Sa', '35000', 'https://static.kfcvietnam.com.vn/images/items/lg/3-taro.jpg?v=gqOojL', '3 Viên Khoai Môn Kim Sa', 7),
(14, '5 Viên Khoai Môn Kim Sa', '55000', 'https://static.kfcvietnam.com.vn/images/items/lg/5-taro.jpg?v=gqOojL', '5 Viên Khoai Môn Kim Sa', 7),
(15, '2 Thanh Bí Phô Mai', '29000', 'https://static.kfcvietnam.com.vn/images/items/lg/2-Pumcheese.jpg?v=gqOojL', '2 Thanh Bí Phô Mai', 7),
(16, '3 Thanh Bí Phô Mai', '39000', 'https://static.kfcvietnam.com.vn/images/items/lg/3-Pumcheese.jpg?v=gqOojL', '3 Thanh Bí Phô Mai', 7),
(17, '5 Thanh Bí Phô Mai', '59000', 'https://static.kfcvietnam.com.vn/images/items/lg/5-Pumcheese.jpg?v=gqOojL', '5 Thanh Bí Phô Mai', 7),
(18, '7Up Lon', '17000', 'https://static.kfcvietnam.com.vn/images/items/lg/7Up-Can.jpg?v=gqOojL', '7Up Lon', 7),
(19, 'Aquafina 500ml', '15000', 'https://static.kfcvietnam.com.vn/images/items/lg/Aquafina-500ml.jpg?v=gqOojL', 'Aquafina 500ml', 7),
(20, 'Pepsi Black Lime Lon', '17000', 'https://static.kfcvietnam.com.vn/images/items/lg/pepsi-lime-can.jpg?v=gqOojL', 'Pepsi Black Lime Lon', 7),
(21, '1 Burger Zinger', '55000', 'https://static.kfcvietnam.com.vn/images/items/lg/Burger-Zinger.jpg?v=gqOojL', '1 Burger Zinger', 6),
(22, '1 Burger Tôm', '45000', 'https://static.kfcvietnam.com.vn/images/items/lg/Burger-Shrimp.jpg?v=gqOojL', '1 Burger Tôm', 6),
(23, '1 Cơm Gà Xiên Que', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-Skewer.jpg?v=gqOojL', '1 Cơm Gà Xiên Que', 6),
(24, '1 Cơm Xiên Gà Tenderods', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-TENDERODS.jpg?v=gqOojL', '1 Cơm Xiên Gà Tenderods', 6),
(25, '1 Cơm Gà Bít-tết', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-Steak.jpg?v=gqOojL', '1 Cơm Gà Bít-tết', 6),
(26, '1 Mì Ý Xốt Cà Xúc Xích Gà Zinger', '61000', 'https://static.kfcvietnam.com.vn/images/items/lg/MY-Y-ZINGER.jpg?v=gqOojL', '1 Mì Ý Xốt Cà Xúc Xích Gà Zinger', 6),
(27, '1 Cơm Gà Rán', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-F.Chicken.jpg?v=gqOojL', '1 Cơm Gà Rán', 6),
(28, '1 Cơm Phi-lê Gà Quay', '46000', 'https://static.kfcvietnam.com.vn/images/items/lg/Rice-Flava.jpg?v=gqOojL', '1 Cơm Phi-lê Gà Quay', 6),
(29, 'Combo Nhóm 1', '175000', 'https://static.kfcvietnam.com.vn/images/items/lg/D6.jpg?v=gqOojL', '3 Miếng Gà + 1 Burger Zinger/Burger Tôm/Burger Phi-lê Gà Quay + 2 Lon Pepsi', 5),
(30, 'Combo Nhóm 3', '232000', 'https://static.kfcvietnam.com.vn/images/items/lg/D8-new.jpg?v=gqOojL', '5 Miếng Gà + 1 Popcorn (Vừa) / 4 Gà Miếng Nuggets+ 2 Pepsi Lon', 5),
(31, 'Combo Mì Ý', '89000', 'https://static.kfcvietnam.com.vn/images/items/lg/D3-new.jpg?v=gqOojL', '1 Mì Ý Xốt Cà Gà Viên + 1 Miếng Gà+ 1 Lon Pepsi Can', 4),
(32, 'Combo Salad Hạt', '79000', 'https://static.kfcvietnam.com.vn/images/items/lg/D4-new.jpg?v=gqOojL', '1 Miếng Gà + 1 Salad Hạt + 1 Lon Pepsi', 4),
(33, 'Combo Burger', '91000', 'https://static.kfcvietnam.com.vn/images/items/lg/D5.jpg?v=gqOojL', '1 Burger Zinger/Burger Gà Quay Flava/Burger Tôm + 1 Miếng Gà Rán + 1 Lon Pepsi', 4),
(34, '3 Bánh Khoai Tây Chiên', '25000', 'https://static.kfcvietnam.com.vn/images/items/lg/3-Hash-Browns.jpg?v=gqOojL', '3 Bánh Khoai Tây Chiên', 3),
(35, '4 Bánh Khoai Tây Chiên', '32000', 'https://static.kfcvietnam.com.vn/images/items/lg/4-Hash-Browns.jpg?v=gqOojL', '4 Bánh Khoai Tây Chiên', 3),
(36, '6 Bánh Khoai Tây Chiên', '47000', 'https://static.kfcvietnam.com.vn/images/items/lg/6-Hash-Browns.jpg?v=gqOojL', '6 Bánh Khoai Tây Chiên', 3),
(37, 'Combo Summer 79k', '79000', 'https://static.kfcvietnam.com.vn/images/items/lg/CBO79K_Summer.jpg?v=gqOojL', '1 Miếng Gà + 1 Gà Viên (Vừa)/ 1 Burger Tôm + 1 lon Pepsi + 1 Phiếu cào', 2),
(38, 'Happy Meal', '89000', 'https://static.kfcvietnam.com.vn/images/items/lg/happy-meal.jpg?v=gqOojL', '3 Miếng Gà + 1 lon 7Up', 2),
(50, 'con heo', '34000', '39.jpg', 'vcbcvb', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_detail`
--

CREATE TABLE `order_detail` (
  `id_order` int(11) NOT NULL,
  `id_order_info` int(11) NOT NULL,
  `id_detail` int(11) NOT NULL,
  `size` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `order_detail`
--

INSERT INTO `order_detail` (`id_order`, `id_order_info`, `id_detail`, `size`, `price`) VALUES
(30, 24, 38, 2, 89000),
(31, 25, 37, 1, 79000),
(32, 26, 36, 1, 47000),
(33, 26, 35, 1, 32000),
(34, 27, 50, 1, 34000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_info`
--

CREATE TABLE `order_info` (
  `id_order` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `address` text NOT NULL,
  `phone_number` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `size` int(11) NOT NULL,
  `total` varchar(255) NOT NULL,
  `date_created` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `order_info`
--

INSERT INTO `order_info` (`id_order`, `user_id`, `address`, `phone_number`, `email`, `size`, `total`, `date_created`) VALUES
(23, 37, 'fghfgh', 237338011, 'thinh@gmail.com', 4, '326000', '2023-07-29'),
(24, 37, 'hjghjg', 237338011, 'thinh@gmail.com', 2, '178000', '2023-07-29'),
(25, 37, 'bnmbnm', 237338011, 'thinh@gmail.com', 1, '79000', '2023-07-29'),
(26, 37, 'jkhjk', 237338011, 'thinh@gmail.com', 2, '79000', '2023-07-29'),
(27, 37, 'vbnvbn', 237338011, 'thinh@gmail.com', 1, '34000', '2023-07-29');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rating`
--

CREATE TABLE `rating` (
  `id_rate` int(10) NOT NULL,
  `rate` varchar(200) NOT NULL,
  `date_created` date NOT NULL,
  `id_food` int(10) NOT NULL,
  `id_user` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
  `id_role` int(11) NOT NULL,
  `name_role` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id_role`, `name_role`) VALUES
(1, 'admin'),
(2, 'user');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL,
  `full_name` varchar(200) NOT NULL,
  `phone_number` varchar(10) NOT NULL,
  `email` varchar(50) NOT NULL,
  `address` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `id_role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`user_id`, `full_name`, `phone_number`, `email`, `address`, `password`, `id_role`) VALUES
(0, 'admin123', '0123456789', 'admin123@gmail.com', '23 user1234', 'c6c86b00755752c8425b2aa6d86eb0bf27e9b0384b1122903429caf9d9c2391b6830106c55860b47b73058387cce9c9baed45e9d1066ae12c530fd850546d169', 1),
(35, 'thinh', '0327338010', 'thinh123@gmail.com', '1832a, KP2, P.Tan Thoi Hiep, Q.12', 'c2aee49dd1a039f4ffb4bbf467a75dd552d34a28ed98a4dbb5c6ddbb57f20293493c9f73d8d1c2c29e51bbdf394cdff273ba61556a5e5c73d794ce6fc39e0a73', 2),
(37, 'thinh123', '0237338011', 'thinh@gmail.com', 'kjkjh', '1f417bf05af6fa4081e62867c2a0fa3214a30051b8b3294d6a34a412c996d01796d3f41d9d5f717992c4b8d784f014c218a780933d41ff24b5c033ac055e657b', 2),
(38, 'Trong Hien', '0906562779', 'tronghien@gmail.com', 'Go Vap', '99a5af2f662fb57812f1d43acca53f28226cfd8c31afec69659f240ddbb368c6330e83359905ddda78dfe699ec35bc162df163b2079079c7ca6cf0b9c60e4df3', 2);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id_cate`);

--
-- Chỉ mục cho bảng `food_detail`
--
ALTER TABLE `food_detail`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `cate_fk` (`id_cate`);

--
-- Chỉ mục cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id_order`),
  ADD KEY `id_order_info` (`id_order_info`,`id_detail`),
  ADD KEY `id_detail` (`id_detail`);

--
-- Chỉ mục cho bảng `order_info`
--
ALTER TABLE `order_info`
  ADD PRIMARY KEY (`id_order`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`id_rate`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_food` (`id_food`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id_role`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `id_role` (`id_role`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `id_cate` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `food_detail`
--
ALTER TABLE `food_detail`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `id_order` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT cho bảng `order_info`
--
ALTER TABLE `order_info`
  MODIFY `id_order` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT cho bảng `rating`
--
ALTER TABLE `rating`
  MODIFY `id_rate` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
  MODIFY `id_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `food_detail`
--
ALTER TABLE `food_detail`
  ADD CONSTRAINT `cate_fk` FOREIGN KEY (`id_cate`) REFERENCES `category` (`id_cate`);

--
-- Các ràng buộc cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`id_detail`) REFERENCES `food_detail` (`id_detail`),
  ADD CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`id_order_info`) REFERENCES `order_info` (`id_order`);

--
-- Các ràng buộc cho bảng `order_info`
--
ALTER TABLE `order_info`
  ADD CONSTRAINT `order_info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Các ràng buộc cho bảng `rating`
--
ALTER TABLE `rating`
  ADD CONSTRAINT `rating_ibfk_3` FOREIGN KEY (`id_food`) REFERENCES `food_detail` (`id_detail`),
  ADD CONSTRAINT `rating_ibfk_4` FOREIGN KEY (`id_user`) REFERENCES `user` (`user_id`);

--
-- Các ràng buộc cho bảng `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id_role`) REFERENCES `role` (`id_role`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
