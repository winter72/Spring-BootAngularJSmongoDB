angular.module('homeApp')
    .directive("myDirective",function () {
        return {
            templateUrl:'directives/first.html',
            link: function f(scope,element,attributes) {

            }
        }
    })