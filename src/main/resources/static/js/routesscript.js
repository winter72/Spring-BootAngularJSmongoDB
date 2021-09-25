angular.module("homeApp")
    .config(["$routeProvider","$locationProvider",function ($routeProvider,$locationProvider) {
      // $locationProvider.html5Mode(true);
        $routeProvider
            .when('/',{
            templateUrl:'views/home.html',
            controller:'firstCtrl'
        })
            .when('/login/:logout?', {
                templateUrl: 'views/login.html',
                controller: 'loginCtrl'
            })
            .when('/registry',{
                templateUrl:'views/registry.html',
                controller:'registryCtrl',
                /*  controllerAs: 'rctrl',
                 data: {
                      pageClass: 'registry'
                  }*/
            })
            .when('/themes',{
                templateUrl:'views/themes.html',
                controller:'themesCtrl'
            })
            .when('/user/:id/:email?', {
                templateUrl: 'one_user/user.html',
                controller: 'userCtrl',
                  resolve: {
                        access: ["AuthService","$location", function (AuthService,location) {
                           if(AuthService.isAuthenticated())
                                return AuthService.isAuthenticated();
                           else
                          location.path('/login');

                          }],
                    }

            })
            .when('/admin/:id',{
                templateUrl:'admin/admin.html',
                controller: 'adminCtrl',
                resolve: {
                    access: ["AuthService","$location", function (AuthService,location) {
                        if(AuthService.isAdmin())
                            return AuthService.isAdmin();
                        else
                            location.path('/login');
                    }],
                }
            })
            .otherwise({redirectTo:'/login'});

    }]);
/*.run(function ($rootScope, $location, AuthService, AUTH_EVENTS) {
    $rootScope.$on('$stateChangeStart', function (event,next, nextParams, fromState) {
        console.log('changeStart',arguments);
        if (!AuthService.isAuthenticated()) {
            console.log(next.name);
            if (next.name !== '/login' && next.name !== '/register') {
                event.preventDefault();
                $location.path('/login');
            }
        }
    });
    $rootScope.$on('$routeChangeSuccess',function (event,current,previous,reject) {
         console.log('changeSuccess',arguments);
    });
})*/