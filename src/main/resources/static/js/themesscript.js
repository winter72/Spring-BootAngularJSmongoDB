angular.module('homeApp')
.controller('themesCtrl',function ($scope,chooseColor) {
    $scope.model={
        message:"Themes",
    }
    $scope.mainColor=chooseColor;
    $scope.changeBackColorT=function () {
        $scope.mainColor.style.backgroundImage="linear-gradient(to right,white 0%,lightgray 100%)";
    }
})
