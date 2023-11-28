package com.my.myhisab.network

import com.my.myhisab.dto.AllTransactionDto
import com.my.myhisab.dto.CustomerListDto
import com.my.myhisab.dto.DashboardDto
import com.my.myhisab.dto.LedgerDto
import com.my.myhisab.dto.LoginDto
import com.my.myhisab.dto.TransctionAllHistoryDto
import com.my.myhisab.dto.UniqueIdDto
import com.my.myhisab.dto.UserHisteryDataDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap


interface ApiInterface {

    @FormUrlEncoded
    @POST(ApiDetails.LogIn)
    fun loginUser(@FieldMap map: HashMap<String, String>): Call<LoginDto>

    @FormUrlEncoded
    @POST(ApiDetails.dashboard)
    fun dashboard(@FieldMap map: HashMap<String, String>): Call<DashboardDto>

    @FormUrlEncoded
    @POST(ApiDetails.customerlist)
    fun customerlist(@FieldMap map: HashMap<String, String>): Call<CustomerListDto>


    @FormUrlEncoded
    @POST(ApiDetails.alltransition)
    fun alltransition(@FieldMap map: HashMap<String, String>): Call<AllTransactionDto>

    @FormUrlEncoded
    @POST(ApiDetails.changePass)
    fun changePass(@FieldMap map: HashMap<String, String>): Call<AllTransactionDto>

    @FormUrlEncoded
    @POST(ApiDetails.todaytrx)
    fun todaytrx(@FieldMap map: HashMap<String, String>): Call<AllTransactionDto>


    @Multipart
    @POST(ApiDetails.addtrx)
    fun addTransaction(
        @PartMap partMap: LinkedHashMap<String, RequestBody>,
        @Part file: List<MultipartBody.Part?>?
    ): Call<AllTransactionDto>

    @FormUrlEncoded
    @POST(ApiDetails.customertransaction)
    fun customertransaction(@FieldMap map: HashMap<String, String>): Call<TransctionAllHistoryDto>

    @FormUrlEncoded
    @POST(ApiDetails.customerpostofficedeposite)
    fun customerpostofficedeposite(@FieldMap map: HashMap<String, String>): Call<TransctionAllHistoryDto>

    @FormUrlEncoded
    @POST(ApiDetails.customerledger)
    fun customerledger(@FieldMap map: HashMap<String, String>): Call<LedgerDto>

    @FormUrlEncoded
    @POST(ApiDetails.searchname)
    fun uniuqeId(@FieldMap map: HashMap<String, String>): Call<UniqueIdDto>

    @FormUrlEncoded
    @POST(ApiDetails.customertransactioncalc)
    fun customertransactioncalc(@FieldMap map: HashMap<String, String>): Call<UserHisteryDataDto>



    /*


        @Multipart
        @POST(ApiDetails.updatekyc)
        fun updatekyc(
            @Part("uid") memberId: RequestBody?,
            @Part adharImage: List<MultipartBody.Part>,
            @Part panimages: List<MultipartBody.Part>,
            @Part photo: List<MultipartBody.Part>,
        ): Call<LogInModel>

        @GET(ApiDetails.reg)
        fun regAmount(): Call<RetailDto>

        @FormUrlEncoded
        @POST(ApiDetails.recharge)
        fun rechargePhone(@FieldMap map: HashMap<String, String>): Call<LogInModel>
        @FormUrlEncoded
        @POST(ApiDetails.dthrecharge)
        fun dthrecharge(@FieldMap map: HashMap<String, String>): Call<LogInModel>

        @FormUrlEncoded
        @POST(ApiDetails.dthOperater)
        fun dthOperater(@FieldMap map: HashMap<String, String>): Call<RechargeDto>

        @FormUrlEncoded
        @POST(ApiDetails.checkremiter)
        fun checkremiter(@FieldMap map: HashMap<String, String>): Call<CheckStatus>

        @FormUrlEncoded
        @POST(ApiDetails.remmiterreg)
        fun remmiterreg(@FieldMap map: HashMap<String, String>): Call<SendMoneyDto>

        @FormUrlEncoded
        @POST(ApiDetails.beneficiarylist)
        fun beneficiarylist(@FieldMap map: HashMap<String, String>): Call<BeneficiaryDto>

        @FormUrlEncoded
        @POST(ApiDetails.fund)
        fun fund(@FieldMap map: HashMap<String, String>): Call<SendMoneyDto>

        @FormUrlEncoded
        @POST(ApiDetails.banklist)
        fun banklist(@FieldMap map: HashMap<String, String>): Call<BankListDto>

        @FormUrlEncoded
        @POST(ApiDetails.addbeneficiary)
        fun addbeneficiary(@FieldMap map: HashMap<String, String>): Call<SendMoneyDto>

        @FormUrlEncoded
        @POST(ApiDetails.fastagoperator)
        fun fastagoperator(@FieldMap map: HashMap<String, String>): Call<WaterBillListDto>

        @FormUrlEncoded
        @POST(ApiDetails.fastagpay)
        fun fastagpay(@FieldMap map: HashMap<String, String>): Call<WaterPay>

        @FormUrlEncoded
        @POST(ApiDetails.licbillfetch)
        fun licbillfetch(@FieldMap map: HashMap<String, String>): Call<LicDataFeatchDto>

        @FormUrlEncoded
        @POST(ApiDetails.licpay)
        fun licpay(@FieldMap map: HashMap<String, String>): Call<WaterPay>

        @FormUrlEncoded
        @POST(ApiDetails.lpgfetch)
        fun lpgfetch(@FieldMap map: HashMap<String, String>): Call<MuniciFetchDto>

        @FormUrlEncoded
        @POST(ApiDetails.lpgpay)
        fun lpgpay(@FieldMap map: HashMap<String, String>): Call<WaterPay>

        @FormUrlEncoded
        @POST(ApiDetails.lpgoperator)
        fun lpgoperator(@FieldMap map: HashMap<String, String>): Call<WaterBillListDto>

        @FormUrlEncoded
        @POST(ApiDetails.municipalityoperator)
        fun municipalityoperator(@FieldMap map: HashMap<String, String>): Call<WaterBillListDto>

        @FormUrlEncoded
        @POST(ApiDetails.municipalityfetch)
        fun municipalityfetch(@FieldMap map: HashMap<String, String>): Call<MuniciFetchDto>

        @FormUrlEncoded
        @POST(ApiDetails.municipalitypay)
        fun municipalitypay(@FieldMap map: HashMap<String, String>): Call<WaterPay>

        @FormUrlEncoded
        @POST(ApiDetails.waterbilloperatorlist)
        fun waterbilloperatorlist(@FieldMap map: HashMap<String, String>): Call<WaterBillListDto>

        @FormUrlEncoded
        @POST(ApiDetails.waterbill_fetch)
        fun waterbill_fetch(@FieldMap map: HashMap<String, String>): Call<WaterBillFetchDto>

        @FormUrlEncoded
        @POST(ApiDetails.waterbillpay)
        fun waterbillpay(@FieldMap map: HashMap<String, String>): Call<WaterPay>

        @FormUrlEncoded
        @POST(ApiDetails.aepsbank)
        fun aepsbank(@FieldMap map: HashMap<String, String>): Call<BankListDto>

        @FormUrlEncoded
        @POST(ApiDetails.aepsenquiry)
        fun aepsenquiry(@FieldMap map: HashMap<String, String>): Call<RequestBalanceDto>

        @FormUrlEncoded
        @POST(ApiDetails.electoperator)
        fun electoperator(@FieldMap map: HashMap<String, String>): Call<ElectricityDto>

        @FormUrlEncoded
        @POST(ApiDetails.elecfetch)
        fun elecfetch(@FieldMap map: HashMap<String, String>): Call<EleFetchDto>

        @FormUrlEncoded
        @POST(ApiDetails.elecpay)
        fun elecpay(@FieldMap map: HashMap<String, String>): Call<ELEPay>

        @FormUrlEncoded
        @POST(ApiDetails.loancategory)
        fun loancategory(@FieldMap map: HashMap<String, String>): Call<LoanListDto>

        @FormUrlEncoded
        @POST(ApiDetails.loanform)
        fun loanform(@FieldMap map: HashMap<String, String>): Call<LoanListDto>


        @POST(ApiDetails.support)
        fun support(): Call<SupportDto>

    */


//    @Multipart
//    @POST(ApiDetails.updatekyc)
//    fun updatekyc(
//        @Part("uid") memberId: RequestBody?,
//        @Part photo: List<MultipartBody.Part>,
//    ): Call<LogInModel>

//    @Multipart
//    @POST(WebServices.UPLOAD_SURVEY)
//    fun uploadSurvey(
//        @Part surveyImage: Array<Part?>?,
//        @Part propertyImage: MultipartBody.Part?,
//        @Part("DRA") dra: RequestBody?
//    ): Call<UploadSurveyResponseModel?>?

//    @POST(ApiDetails.fundrequest)
//    Call<FundMoneyBean> uploadImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
//    @Part("uid") RequestBody uid,
//    @Part("depositedate") RequestBody depositedate,
//    @Part("receiptno") RequestBody receiptno,
//    @Part("amount") RequestBody amount);


}