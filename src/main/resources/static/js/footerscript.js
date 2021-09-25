angular.module('homeApp')
.controller('footerCtrl',['$scope',function ($scope) {

    $scope.$on("$routeChangeSuccess", function (event,args) {
        let chc=args.$$route.originalPath;
        if(chc==='/login'||chc==='/registry'||chc==='/user/:id/:email?'||chc==='/login/:logout?'){
            $scope.footerShow=true;
        }else {
            $scope.footerShow=false;
        }
    })
}]);