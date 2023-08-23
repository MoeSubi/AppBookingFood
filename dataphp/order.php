<?php
include "connect.php";
$phone_number = isset($_POST['phone_number']) ? $_POST['phone_number'] : '';
$email = isset($_POST['email']) ? $_POST['email'] : '';
$total = isset($_POST['total']) ? $_POST['total'] : '';
$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : '';
$address = isset($_POST['address']) ? $_POST['address'] : '';
$size = isset($_POST['size']) ? $_POST['size'] : '';
$date_created = isset($_POST['date_created']) ? $_POST['date_created'] : '';
$detail = isset($_POST['detail']) ? $_POST['detail'] : '';

$query = 'INSERT INTO `order_info`(`user_id`, `address`, `phone_number`, `email`, `size`, `total`, `date_created`) VALUES ('.$user_id.',"'.$address.'","'.$phone_number.'","'.$email.'",'.$size.',"'.$total.'", "'.$date_created.'")';

$data = mysqli_query($conn, $query);
if ($data == true) 
{
    $query = 'SELECT id_order FROM `order_info` WHERE `user_id` = '.$user_id.' ORDER BY `id_order` DESC LIMIT 1';
    $data = mysqli_query($conn, $query);
    if ($data !== false) {
        $id_order_detail = mysqli_fetch_assoc($data);
        if (!empty($id_order_detail)) 
        {
            // co don hang
            $detail = json_decode($detail, true);
            foreach ($detail as $key => $value) 
            {
                $query1 = 'INSERT INTO `order_detail`(`id_order_info`, `id_detail`, `size`, `price`) VALUES ('.$id_order_detail["id_order"].','.$value["id_detail"].','.$value["size"].',"'.$value["price"].'")';
                $data = mysqli_query($conn, $query1);
            }
            if ($data == true) 
            {
                $arr = [
                    'success' => true,
                    'message' => "thanh cong",
                ];
            }
            else
            {
                $arr = [
                    'success' => false,
                    'message' => "khong thanh cong",
                ];
            }
        }
        else
        {
            $arr = [
                'success' => false,
                'message' => "khong thanh cong",
            ];
        }
    } else {
        $arr = [
            'success' => false,
            'message' => mysqli_error($conn),
        ];
    }
}
else
{
    $arr = [
        'success' => false,
        'message' => "khong thanh cong",
    ];
}
print_r(json_encode($arr));
?>
