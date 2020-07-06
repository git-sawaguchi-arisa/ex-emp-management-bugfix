$(function () {
    $("form").validate({
        rules: {
            "name": {
                required: true,
                maxlength: 50
            },
            "gender": {
                required: true,
            },
            "hireDate": {
                required: true,
                date: true
            },
            "mailAddress": {
                required: true,
                email: true
            },
            "zipCode": {
                required: true,
                yubinbango: true
            },
            "address": {
                required: true
            },
            "telephone": {
                required: true,
                phone: true
            },
            "salary": {
                required: true,
                number: true,
                maxlength: 8
            },
            "characteristics": {
                required: true,
            },
            "dependentsCount": {
                required: true,
                number: true,
                maxlength: 3
            }
        },
        messages: {
            "name": {
                required: "名前を入力してください",
                maxlength: "50文字以内で入力してください"
            },
            "gender": {
                required: "性別を選択してください",
            },
            "hireDate": {
                required: "入社日を入力してください",
                date: "日付が不正です"
            },
            "mailAddress": {
                required: "メールアドレスを入力してください",
                email: "メールアドレスの形式が不正です。"
            },
            "zipCode": {
                required: "郵便番号を入力してください",
            },
            "address": {
                required: "住所を入力してください"
            },
            "telephone": {
                required: "電話番号を入力してください",
            },
            "salary": {
                required: "給与を入力してください",
                number: "半角英数字で入力してください",
                maxlength: "8桁以下で入力してください"
            },
            "characteristics": {
                required: "特性を入力してください",
            },
            "dependentsCount": {
                required: "扶養人数を入力してください",
                number: "半角英数字で入力してください",
                maxlength: "3桁の数値を入力してください",
            }
        },
    });
});

jQuery.validator.addMethod(
    "yubinbango",
    function(val,elem){
        reg = new RegExp("[0-9]{3}-[0-9]{4}");
        return this.optional(elem) || reg.test(val);
    },
    "郵便番号が不正です。ハイフン付きで入力してください。",
    "phone",
    function(val,elem){
        reg = new RegExp("^\\d{2,4}-\\d{2,4}-\\d{4}$");
        return this.optional(elem) || reg.test(val);
    },
    "電話番号の形式が不正です。ハイフン付きで入力してください。"
);

jQuery.validator.addMethod(
    "phone",
    function(val,elem){
        reg = new RegExp("^\\d{2,4}-\\d{2,4}-\\d{4}$");
        return this.optional(elem) || reg.test(val);
    },
    "電話番号の形式が不正です。ハイフン付きで入力してください。"
);