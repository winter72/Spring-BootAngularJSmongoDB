let wh1;
let hw1;
let hhh;
let doc1;
let sh;
$( document ).ready(function() {
     hhh=parseInt($(window).height());
    let hwm=parseInt(hhh)+'px';
     hw1=parseInt(hhh)+'px';
     wh1=parseInt(hhh/4)+'px';
     sh=parseInt((hhh/2)+(hhh/4))+'px';
    $('#startContainer1').css('height',hwm);
  //  $('#startContainer').css('height',sh);
    doc1= document.getElementById("startContainer1");
});

var homeApp=angular.module('homeApp',["ngRoute","ngAnimate"]);
homeApp.controller('mainCtrl',['$scope','$window','AuthService',function ($scope,$window,AuthService) {
    $window.onload = function() {
        console.log('Window ready');
        console.log(AuthService.getUser());
    }

}]);
/*homeApp.run(function ($rootScope) {
    $rootScope.$on('$routeChangeSuccess',function (event,current,previous,reject) {*/
       // console.log('changeSuccess',arguments);
       // $rootScope.currentPath=current.$$route.originalPath;
      //  console.log('changeSuccess',$rootScope.currentPath);
   /* })
})*/

homeApp.controller('firstCtrl',['$scope',function ($scope) {
    $scope.model={
        message:"Conference",
    }
    let count=100;
    let current=0;
    let evennumber=1;
    let doc=document.getElementById("startContainer");
    const path=['../img/conference_one.jpg','../img/conference_two.jpg',
        '../img/conference_three.jpeg','../img/conference_four.jpg','../img/conference_crop.jpg'];
   $scope.startLoad=function () {
       doc1.style.backgroundImage="radial-gradient(orangered,yellow,blue)";
      $( document ).ready(function () {
           $('#startContainer').css('height',hw1);
           $('#secondContainer').css('padding-top',wh1);
          let promise = new Promise((resolve) => {
              doc.style.backgroundImage = "linear-gradient(to right,yellow 0%,blue 100%),url(" + path[0] + ")";
              resolve();
          });
       });


          setInterval(()=>{

           if(evennumber%2===0){
               count++;
               doc.style.backgroundSize=count+"%"+count+"%";
               if(count===100 ){
                   evennumber--;
               }
           }else if(evennumber%2!==0){
               setTimeout(()=>{
                   count--;
                   doc.style.backgroundSize = count + "%" + count + "%";
                   if(count===0) {
                       if(current+1==path.length) {
                           current = 0;
                       } else {
                           current++;
                       }
                       doc.style.backgroundImage = "linear-gradient(to right,yellow 0%,blue 100%),url(" + path[current] + ")";
                       evennumber++;
                     }
                   },125)


           }


       },250)
   }

  /* $scope.$watch('doc.style.backgroundImage',function (newval,oldval) {
       console.log(newval)
       doc.style.backgroundImage=newval;
       });

    $scope.$watch('doc.style.backgroundSize',function (newval,oldval) {
        console.log(newval)
        doc.style.backgroundSize=newval
    });*/

}]);


homeApp.factory("chooseColor",function () {
  return doc1;
});