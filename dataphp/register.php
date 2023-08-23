<?php
include "connect.php";
$full_name = isset($_POST['full_name']) ? $_POST['full_name'] : '';
$phone_number = isset($_POST['phone_number']) ? $_POST['phone_number'] : '';
$email = isset($_POST['email']) ? $_POST['email'] : '';
$address = isset($_POST['address']) ? $_POST['address'] : '';
$password = isset($_POST['password']) ? $_POST['password'] : '';
$pwHash=hash_hmac("sha512", $password, "hello");
//check data
$query = 'SELECT * FROM `user` WHERE `email` = "'.$email.'"';
$query1 = 'SELECT * FROM `user` WHERE `phone_number` = "'.$phone_number.'"';
$data = mysqli_query($conn, $query);
$data1 = mysqli_query($conn, $query1);
$numrow = mysqli_num_rows($data);
$numrow1 = mysqli_num_rows($data1);
$id_role = 2;
if ($numrow > 0){
    $arr = [
        'success' => false, 'message' => "Email already exist!"
    ];
}else if($numrow1 > 0){
    $arr = [
        'success' => false, 'message' => "Phone number already exist!"
    ];
}
else{
    //insert
        $query = 'INSERT INTO `user`(`full_name`, `phone_number`, `email`, `address`, `password`,
        `id_role`) VALUES ("'.$full_name.'","'.$phone_number.'", "'.$email.'","'.$address.'","'.$pwHash.'","'.$id_role.'")';
        $data = mysqli_query($conn, $query);

        if($data == true){
            $arr = [
        'success' => true, 
        'message' =>"Register Successful!"        
            ];
            }else{
            $arr = [
        'success' => false, 
        'message' =>"Register Unsuccessful!"
                
            ];       
    }
}
print_r(json_encode($arr));
?>