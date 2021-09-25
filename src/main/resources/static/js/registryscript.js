angular.module('homeApp')
    .controller('registryCtrl',function ($scope,chooseColor) {

        $scope.model={
            message:"Registry"
        };
      /*  $scope.lastvals=function () {
           let result="";
           let csfrhead='0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM';
          let max_position = csfrhead.length - 1;
            for(let i = 0; i < 10; ++i ) {
               let position = Math.floor ( Math.random() * max_position );
                result = result + csfrhead.substring(position, position + 1);
            }
            return result;
        }*/
        $scope.mainColorR=chooseColor;
        $scope.changeBackColorR=function () {
            $scope.mainColorR.style.backgroundImage="radial-gradient(greenyellow,lawngreen,green)";
        };
    })
    .controller("validetionCtrl",['$scope','API_ENDPOINT','AllUrls',function($scope,API_ENDPOINT,AllUrls){
        $scope.myVar=true;
        $scope.AddUser=AllUrls;
        $scope.AddUser.setPathUrl(API_ENDPOINT.urlRegistry);

    }]);
