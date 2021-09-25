angular.module('homeApp')
.service('AuthService',['$q','$http','$rootScope','$window','API_ENDPOINT',function ($q,$http,$rootScope,$window,API_ENDPOINT) {
    var AUTH_HEADER_NAME = 'Authorization',
        HTTP_TYPES_TO_ADD_TOKEN = ['DELETE', 'POST', 'PUT','GET'];
    var isAuthenticated = false;
    var isAdmin=false;
    var authToken;
    let ROUTE_USER;
    let userrs={
        id:'',
        email:'',
        role:'',
        firstname:'',
        surname:'',
        photo:''
      };

    function userRules() {
        if($window.localStorage.getItem('ROLE')=='ROLE_USER') isAuthenticated = true;
        if($window.localStorage.getItem('ROLE')=='ROLE_ADMIN') isAdmin=true;
    }

    var setUserFoto=function (fotos) {
        userrs.photo=fotos;
    }

    var setUser=function (id,email,role,firstname,surname,photo) {
       userrs.id=id;
       userrs.email=email;
       userrs.role=role;
       userrs.firstname=firstname;
       userrs.surname=surname;
       setUserFoto(photo);
       $window.localStorage.setItem('IDUSER',userrs.id);
       $window.localStorage.setItem('EMAIL',userrs.email);
       $window.localStorage.setItem('ROLE',userrs.role);
       $window.localStorage.setItem('FIRSTNAME',userrs.firstname);
       $window.localStorage.setItem('SURNAME',userrs.surname);
      setFoto(userrs.photo);
        userRules();
    }


    function userEvent(id) {
       $rootScope.$broadcast("messageEvent", {
            message: "/user/"+id,

        });
    }
    function loadUserCredentials() {
        var token = $window.localStorage.getItem(AUTH_HEADER_NAME);
        let i=$window.localStorage.getItem('IDUSER');
        let e=$window.localStorage.getItem('EMAIL');
        let r=$window.localStorage.getItem('ROLE');
        let f=$window.localStorage.getItem('FIRSTNAME');
        let s=$window.localStorage.getItem('SURNAME');
        let im=$window.localStorage.getItem('IMAGE');
        if (token) {
            useCredentials(token);
            setUser(i,e,r,f,s,im);
        }
    }

   var storeUserCredentials= function (token,id) {
        $window.localStorage.setItem(AUTH_HEADER_NAME, token);
        let one='';
        if(isAdmin) {
           one= '/admin/' + id;
        }else if(isAuthenticated){
            one='/user/'+id;
       }
       $window.localStorage.setItem(ROUTE_USER,one);

       userEvent(id);
        useCredentials(token);

    }

    function useCredentials(token) {
       /* if($window.localStorage.getItem('ROLE')=='ROLE_USER') isAuthenticated = true;
        if($window.localStorage.getItem('ROLE')=='ROLE_ADMIN') isAdmin=true;*/
userRules();
        authToken = token;
        // Set the token as header for your requests!
        $http.defaults.headers.common.Authorization = authToken;

    }

    function destroyUserCredentials() {
        authToken = undefined;
        if($window.localStorage.getItem('ROLE')=='ROLE_USER')isAuthenticated = false;
        if($window.localStorage.getItem('ROLE')=='ROLE_ADMIN') isAdmin=false;
        userrs.id='';
        userrs.email='';
        userrs.role='';
        userrs.firstname='';
        userrs.surname='';
        $http.defaults.headers.common.Authorization = undefined;
        $window.localStorage.removeItem(AUTH_HEADER_NAME);
        $window.localStorage.removeItem(ROUTE_USER);
        $window.localStorage.removeItem('IDUSER');
        $window.localStorage.removeItem('EMAIL');
        $window.localStorage.removeItem('ROLE');
        $window.localStorage.removeItem('FIRSTNAME');
        $window.localStorage.removeItem('SURNAME');
        $window.localStorage.removeItem('IMAGE');
    }

var setFoto=function (text) {
    $window.localStorage.removeItem('IMAGE');
    $window.localStorage.setItem('IMAGE',text);
}
    var logout = function() {
        destroyUserCredentials();
    };

    loadUserCredentials();
    return{
        storeUserCredentials:storeUserCredentials,
        setUser:setUser,
        setUserFoto:setUserFoto,
        setFoto:setFoto,
        logout: logout,
        userRoute:function(){return window.localStorage.getItem(ROUTE_USER);},
        getUser:function(){return userrs;},
        isAuthenticated: function() {return isAuthenticated;},
        isAdmin:function () {return isAdmin;}
    }
}])
.factory('AuthInterceptor',['$rootScope','$q','AUTH_EVENTS',function ($rootScope,$q,AUTH_EVENTS) {
    return{
        responseError: function (response) {
            $rootScope.$broadcast({
                401: AUTH_EVENTS.notAuthenticated,
            }[response.status], response);
            console.log(authToken)
            return $q.reject(response);
        }
    }
}])
.config(['$httpProvider',function ($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
}]);