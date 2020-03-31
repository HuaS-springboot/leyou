import baseApi from "@/api/index";
const url = "/login";
const loginApi = {

    loginByCode(phone, code) {
        return baseApi({
            url: url + "/loginByCode",
            method: "post",
            data: { code, phone }
        });
    }
};
export default loginApi;
