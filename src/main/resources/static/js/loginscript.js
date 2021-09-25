angular.module('homeApp')
.controller('loginCtrl',['$scope','$routeParams','chooseColor',function ($scope,$routeParams,chooseColor) {
    $scope.model={
        message:"Login",
    }
    $scope.mainColor=chooseColor;
    $scope.changeBackColor=function () {
        $scope.mainColor.style.backgroundImage="radial-gradient(white,lightgray,gray)";
    }
    console.log($routeParams.logout);
    if($routeParams.logout==='exit'){
        $scope.showLogout = true;
    }else {
        $scope.showLogout = false;
    }

}])
    .controller('validateCtrl',['$scope','API_ENDPOINT','AllUrls' ,function($scope,API_ENDPOINT,AllUrls) {
        $scope.myVar;
        $scope.CheckUser=AllUrls;
     //   $scope.CheckUser.setAccess(true);
        $scope.CheckUser.setMsg();
        $scope.CheckUser.setPathUrl(API_ENDPOINT.urlLogin);

    }]);
