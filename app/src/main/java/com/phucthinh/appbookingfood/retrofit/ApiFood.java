package com.phucthinh.appbookingfood.retrofit;

import com.phucthinh.appbookingfood.model.FoodCategoryModel;
import com.phucthinh.appbookingfood.model.FoodDetailModel;
import com.phucthinh.appbookingfood.model.MessageModel;
import com.phucthinh.appbookingfood.model.OrderModel;
import com.phucthinh.appbookingfood.model.ThongKeModel;
import com.phucthinh.appbookingfood.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiFood {
    @GET("getcategory.php")
    Observable<FoodCategoryModel> getFoodCategory();

    @GET("getfooddetail.php")
    Observable<FoodDetailModel> getFoodDetail();

    @POST("detail.php")
    @FormUrlEncoded
    Observable<FoodDetailModel> getFood(
            @Field("page") int page,
            @Field("id_cate") int id_cate
    );
    @POST("register.php")
    @FormUrlEncoded
    Observable<UserModel> getRegister(
            @Field("full_name") String fullName,
            @Field("phone_number") String phoneNumber,
            @Field("email") String email,
            @Field("address") String address,
            @Field("password") String password,
            @Field("id_role") int id_role
    );
    @POST("login.php")
    @FormUrlEncoded
    Observable<UserModel> getLogin(
            @Field("email") String email,
            @Field("password") String password

    );
    @POST("search.php")
    @FormUrlEncoded
    Observable<FoodDetailModel> search(
            @Field("search") String search
    );
    @POST("order.php")
    @FormUrlEncoded
    Observable<UserModel> createOrder(
            @Field("email") String email,
            @Field("phone_number") String phoneNumber,
            @Field("total") String total,
            @Field("user_id") int user_id,
            @Field("address") String address,
            @Field("size") int size,
            @Field("detail") String detail,
            @Field("date_created") String currentTime
    );


    @POST("insertsp.php")
    @FormUrlEncoded
    Observable<MessageModel> getInsertsp(
            @Field("food_name") String food_name,
            @Field("price") String price,
            @Field("image_food") String image_food,
            @Field("description") String description,
            @Field("id_cate") int id_cate
    );

    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);

    @POST("deletesp.php")
    @FormUrlEncoded
    Observable<MessageModel> xoaSanPham(
            @Field("id_detail") int id
    );

    @POST("updatesp.php")
    @FormUrlEncoded
    Observable<MessageModel> getUpdatetsp(
            @Field("food_name") String food_name,
            @Field("price") String price,
            @Field("image_food") String image_food,
            @Field("description") String description,
            @Field("id_cate") int id_cate,
            @Field("id_detail") int id_detail
    );

    @POST("vieworder.php")
    @FormUrlEncoded
    Observable<OrderModel> viewOrder(
            @Field("user_id") int user_id,
            @Field("id_role") int id_role
    );
    @GET("thongke.php")
    Observable<ThongKeModel> getThongKe();

    @POST("addUser.php")
    @FormUrlEncoded
    Observable<MessageModel> addUser(
            @Field("full_name") String full_name,
            @Field("phone_number") String phone_number,
            @Field("email") String email,
            @Field("address") String address,
            @Field("password") String password,
            @Field("id_role") int id_role

    );
    @POST("editUser.php")
    @FormUrlEncoded
    Observable<MessageModel> editUser(
            @Field("full_name") String full_name,
            @Field("phone_number") String phone_number,
            @Field("email") String email,
            @Field("address") String address,
            @Field("password") String password,
            @Field("id_role") int id_role,
            @Field("user_id") int user_id
    );
    @POST("editUserCustomer.php")
    @FormUrlEncoded
    Observable<MessageModel> editUserCustomer(
            @Field("full_name") String full_name,
            @Field("phone_number") String phone_number,
            @Field("email") String email,
            @Field("address") String address,
            @Field("user_id") int user_id
    );
    @POST("removeUser.php")
    @FormUrlEncoded
    Observable<MessageModel> rmvUser(
            @Field("user_id") int user_id

    );
    @POST("addCate.php")
    @FormUrlEncoded
    Observable<MessageModel> addCate(
            @Field("category_name") String category_name

    );
    @POST("editCate.php")
    @FormUrlEncoded
    Observable<MessageModel> editCate(
            @Field("category_name") String category_name,
            @Field("id_cate") int id_cate

    );
    @POST("removeCate.php")
    @FormUrlEncoded
    Observable<MessageModel> rmvCate(
            @Field("id_cate") int id_cate
    );
    @GET("getUser.php")
    Observable<UserModel> getUser();

}
