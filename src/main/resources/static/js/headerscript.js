angular.module('homeApp')
.controller('headerCtrl',['$scope','AuthService','$rootScope',function ($scope,AuthService,$rootScope) {
    $scope.headers="Header";

    $scope.$on("messageEvent", function (event, args) {
        $scope.info=args.message;
       $scope.accessShow=true;
        $scope.accessShow1=true;
      console.log($scope.info);
        event.preventDefault();
      event.stopPropagation;
    })

    $scope.$on("$routeChangeSuccess", function (event,args) {
        let ms;
        if(AuthService.isAuthenticated()||AuthService.isAdmin()){
            $scope.accessShow=true;
            $scope.info=AuthService.userRoute();

        }else {
            $scope.accessShow=false;
            $scope.accessShow1=false;
            $scope.info='/login';
        }
        let chc=args.$$route.originalPath;
        if(chc==='/'||chc==='/registry'||chc==='/themes'||chc==='/login/:logout?'){
            $scope.accessShow1=false;
        }else if(chc==='/user/:id/:email?' || chc==='/admin/:id'){
            $scope.counterClick=0;
            $scope.accessShow1=true;
        }
       // console.log('Success',args.$$route.originalPath);
    })

   setInterval(()=>{
       angular.element(document.getElementById("localtime")).text(new Date().toLocaleTimeString());
   },1);


$scope.exitUsers=function(){
        $scope.accessShow=false;
    $scope.accessShow1=false;
        AuthService.logout();
    }


    $scope.showUserMenu=function () {
        console.log($scope.counterClick);
        let vs;
        $scope.counterClick++;
        if($scope.counterClick%2!==0){
            vs=true;
        }else{
            vs=false;
        }
        $rootScope.$broadcast('OddOrEven',{
            mainmessage:vs,
        })
        if($scope.counterClick%2===0){
            $scope.counterClick=0;
        }
    }

}])
