angular.module('homeApp')
.factory('AllUrls',['$http','$location','AuthService',function ($http,$location,AuthService) {
   let msg='';
   let pathUrl;
    return {
       setPathUrl:function(text){
           pathUrl=text;
           console.log(pathUrl);
       },
        setMsg:function(){
          msg='';
        },
        createOrLoginOrRegistry:function (user) {
                $http.post(pathUrl, JSON.stringify(user))
                    .then(function (resp) {
                            console.log(resp.data);
                            if (resp['status'] == 200) {
                                AuthService.setUser(resp.data['id'], resp.data['email'], resp.data['role'], resp.data['firstname'], resp.data['surname'], resp.data['photo_user']);
                                AuthService.storeUserCredentials(resp.data['jwttoken'], resp.data['id']);
                                let r = resp.data['role'];
                                usr=AuthService.getUser();
                                if (r == 'ROLE_USER') {
                                    $location.path("/user/" + resp.data['id']);
                                } else if (resp.data['role'] == 'ROLE_ADMIN') {
                                    $location.path("/admin/" + resp.data['id']);
                                }
                            }else if(resp['status']==201){
                                msg='Successfully' ;
                            }
                        }, function (resp) {
                            msg = "Incorrect email or password";
                        }
                    )

            },
           updateUsers:function(user){
               $http.put(pathUrl,JSON.stringify(user))
                   .then(function successCallback(resp) {
                       AuthService.setUser(resp.data['id'], resp.data['email'], resp.data['role'], resp.data['firstname'], resp.data['surname'], resp.data['photo_user']);
                       AuthService.storeUserCredentials(resp.data['jwttoken'], resp.data['id']);
                       msg="Successfully";
                   },function errorCallback(resp) {
                       msg='Mistake of the server.Try later.'
                   });
           },
            getMsg:function () {
                return msg;
            },
    };
}])