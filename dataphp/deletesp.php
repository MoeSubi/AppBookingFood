<?php
include "connect.php";
$id_detail = isset($_POST['id_detail']) ? $_POST['id_detail'] : '';



$query = "DELETE FROM `food_detail` WHERE `id_detail`=".$id_detail; 
$data = mysqli_query($conn, $query);

if ($data == true) {
        $arr = [
          'success' => true,
          'message' => "Xóa thành công"
        ];
}else {
  $arr = [
    'success' => false,
    'message' => "Xóa không thành công"
  ];
}

print_r(json_encode($arr));

?>

