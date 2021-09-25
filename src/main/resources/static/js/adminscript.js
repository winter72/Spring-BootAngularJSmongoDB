angular.module('homeApp')
.controller('adminCtrl',['$scope','$routeParams','chooseColor',function ($scope,$routeParams,chooseColor) {
    $scope.model={
        message:"Admin:"+$routeParams.id,//.replace(/:/,""),
    }

    $scope.mainColorU=chooseColor;

    $scope.changeBackColorU=function () {
        $scope.mainColorU.style.backgroundImage = "linear-gradient(to right,white 0%,whitesmoke 100%)";//radial-gradient(white,whitesmoke,lightgray,)";
    }
}])