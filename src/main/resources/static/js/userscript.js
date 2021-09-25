angular.module('homeApp')
    .controller('userCtrl',['$scope','$rootScope','$routeParams','chooseColor','API_ENDPOINT','AllUrls','AuthService',function ($scope,$rootScope,$routeParams,chooseColor,API_ENDPOINT,AllUrls,AuthService) {
        $scope.model={
            message:"User:"+$routeParams.id,//.replace(/:/,""),
        }
        $scope.updateUser=AllUrls;
        $scope.updateUser.setMsg();
        $scope.updateUser.setPathUrl(API_ENDPOINT.urlUpdate);
        $scope.respStyle={
            color:'',
            'font-weight':'bold',
        };
       $scope.checkingUser= function(){
            $scope.checkUser = AuthService.getUser();
           let linkfoto=$scope.checkUser.photo;
           $scope.fotolink={
               photolink:linkfoto,
               userlink:$routeParams.id
           };

        }

        $scope.mainColorU=chooseColor;

        $scope.changeBackColorU=function () {
            if($scope.updateUser.getMsg()==='Successfully'){
                $scope.respStyle.color='darkgreen';
            }else {
                $scope.respStyle.color='red';
            }
            $scope.mainColorU.style.backgroundImage = "linear-gradient(to right,white 0%,lightgray 100%)";//radial-gradient(white,whitesmoke,lightgray,)";
        }

        $scope.yourChoose=function (text) {
            $scope.finalchoose=text;
        }

        $scope.$on('OddOrEven',function (event,args) {
            $scope.userBarMenu=args.mainmessage;
           event.preventDefault();
            event.stopPropagation;
        });

       $scope.checkingUser();

    }]);
