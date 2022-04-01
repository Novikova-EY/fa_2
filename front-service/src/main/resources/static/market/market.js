angular.module('market').controller('marketController', function ($scope, $http, $localStorage) {
    const corePath = 'http://localhost:5555/core';
    const cartPath = 'http://localhost:5555/cart';

    $scope.loadProducts = function (pageIndex = 1) {
        $scope.currentPageIndex = pageIndex;
        $http({
            url: corePath + '/api/v1/products',
            method: 'GET',
            params: {
                p: pageIndex,
                title_part: $scope.filter ? $scope.filter.title_part : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            console.log(response);
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.nextPage = function () {
        if ($scope.currentPageIndex < $scope.productsPage.totalPages)
            $scope.loadProducts($scope.currentPageIndex + 1);
    }

    $scope.prePage = function () {
        if ($scope.currentPageIndex !== 1)
            $scope.loadProducts($scope.currentPageIndex - 1);
    }

    $scope.showProductInfo = function (productId) {
        $http.get(corePath + '/api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }

    $scope.deleteProductById = function (productId) {
        $http.delete(corePath + '/api/v1/products/' + productId).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.addToCart = function (productId) {
        $http.get(cartPath + '/api/v1/cart/' + $localStorage.marketGuestCartId + '/add/' + productId).then(function (response) {
        });
    }

    $scope.loadProducts();

});
