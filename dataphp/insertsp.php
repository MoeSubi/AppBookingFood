<?php
include "connect.php";
$food_name = isset($_POST['food_name']) ? $_POST['food_name'] : '';
$price = isset($_POST['price']) ? $_POST['price'] : '';
$image_food = isset($_POST['image_food']) ? $_POST['image_food'] : '';
$description = isset($_POST['description']) ? $_POST['description'] : '';
$id_cate = isset($_POST['id_cate']) ? $_POST['id_cate'] : '';


$query = "INSERT INTO food_detail (food_name, price, image_food, description, id_cate) 
VALUES ('$food_name', '$price', '$image_food', '$description', '$id_cate')"; 
$data = mysqli_query($conn, $query);

if ($data == true) {
        $arr = [
          'success' => true,
          'message' => "Thêm thành công"
        ];
}else {
  $arr = [
    'success' => false,
    'message' => "Thêm không thành công"
  ];
}

print_r(json_encode($arr));

?>

