<?php
include "connect.php";
$query = "SELECT * FROM `category`";
$data = mysqli_query($conn, $query);
$result = array();
while($row = mysqli_fetch_assoc($data)){
  $result[] = ($row);
}

if(!empty($result)){
  
  $arr = [
    'success' => true,
    'message' => "Get all the caterogies successful!!!",
    'result' => $result
  ];
}else{
  $arr = [
    'success' => false,
    'message' => "Get all the caterogies unsuccessful!!!",
    'result' => $result
  ];
}

print_r(json_encode($arr));

?>

