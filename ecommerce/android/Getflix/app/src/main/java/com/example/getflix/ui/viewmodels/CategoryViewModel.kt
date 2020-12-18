package com.example.getflix.ui.viewmodels

import androidx.lifecycle.*
import com.example.getflix.models.CategoryModel
import com.example.getflix.models.ProductModel
import com.example.getflix.models.SubcategoryModel

class CategoryViewModel() : ViewModel() {

    private val _categories = MutableLiveData<HashMap<Int, CategoryModel>>()
    val categories: LiveData<HashMap<Int, CategoryModel>>
        get() = _categories

    private val _displayedCategory = MutableLiveData<CategoryModel>()
    val displayedCategory: LiveData<CategoryModel>
        get() = _displayedCategory

    fun setCategory(id: Int) {
        var categoryModel: CategoryModel? = null
        if (id == 1) {
            var zaraJacket1 =
                    ProductModel(1, "Jacket", "222", "1", "Zara", 1, 1, 1, "Nice jacket", "1", "1", "1", "1")
            var zaraJacket2 =
                    ProductModel(2, "Jacket", "231", "1", "Zara", 1, 1, 1, "Cool jacket", "1", "1", "1", "1")
            var zaraJacket3 =
                    ProductModel(3, "Jacket", "321", "1", "Zara", 1, 1, 1, "Amazing jacket", "1", "1", "1", "1")
            var jackets = mutableListOf<ProductModel>(zaraJacket1, zaraJacket2, zaraJacket3)
            var subcategoryModel2 =
                    SubcategoryModel("Jackets", mutableListOf(zaraJacket1, zaraJacket2, zaraJacket3))
            var zaraSkirt1 =
                    ProductModel(4, "Skirt", "79", "1", "Zara", 1, 1, 1, "Nice skirt", "1", "1", "1", "1")
            var zaraSkirt2 =
                    ProductModel(5, "Skirt", "93", "1", "Zara", 1, 1, 1, "Cool skirt", "1", "1", "1", "1")
            var zaraSkirt3 =
                    ProductModel(6, "Skirt", "102", "1", "Zara", 1, 1, 1, "Amazing skirt", "1", "1", "1", "1")
            var skirts = mutableListOf<ProductModel>(zaraSkirt1, zaraSkirt2, zaraSkirt3)
            var zaraDress1 =
                    ProductModel(7, "Dress", "60", "1", "Zara", 1, 1, 1, "Nice dress", "1", "1", "1", "1")
            var zaraDress2 =
                    ProductModel(8, "Dress", "142", "1", "Zara", 1, 1, 1, "Cool dress", "1", "1", "1", "1")
            var zaraDress3 =
                    ProductModel(9, "Dress", "201", "1", "Zara", 1, 1, 1, "Amazing dress", "1", "1", "1", "1")
            var dresses = mutableListOf<ProductModel>(zaraDress1, zaraDress2, zaraDress3)
            var subcategoryModel1 = SubcategoryModel("Skirts", skirts)
            var subcategoryModel3 = SubcategoryModel("Dresses", dresses)
            var subcategories = mutableListOf<SubcategoryModel>(
                    subcategoryModel1,
                    subcategoryModel3,
                    subcategoryModel2
            )
            categoryModel = CategoryModel("Woman", subcategories)
        } else if (id == 2) {
            var zaraJacket1 =
                    ProductModel(10, "Jacket", "222", "1", "Zara", 1, 1, 1, "Nice jacket", "1", "1", "1", "1")
            var zaraJacket2 =
                    ProductModel(11, "Jacket", "231", "1", "Zara", 1, 1, 1, "Cool jacket", "1", "1", "1", "1")
            var zaraJacket3 =
                    ProductModel(
                            12,
                            "Jacket",
                            "321",
                            "1",
                            "Zara",
                            1,
                            1,
                            1,
                            "Amazing jacket",
                            "1",
                            "1",
                            "1",
                            "1"
                    )
            var jackets = mutableListOf<ProductModel>(zaraJacket1, zaraJacket2, zaraJacket3)
            var subcategoryModel2 =
                    SubcategoryModel("Jeans", mutableListOf(zaraJacket1, zaraJacket2, zaraJacket3))
            var zaraJean1 =
                    ProductModel(13, "Jean", "79", "1", "Zara", 1, 1, 1, "Nice jean", "1", "1", "1", "1")
            var zaraJean2 =
                    ProductModel(14, "Jean", "93", "1", "Zara", 1, 1, 1, "Cool jean", "1", "1", "1", "1")
            var zaraJean3 =
                    ProductModel(15, "Jean", "102", "1", "Zara", 1, 1, 1, "Amazing jean", "1", "1", "1", "1")
            var jeans = mutableListOf<ProductModel>(zaraJean1, zaraJean2, zaraJean3)
            var zaraShirt1 =
                    ProductModel(16, "Shirt", "60", "1", "Zara", 1, 1, 1, "Nice shirt", "1", "1", "1", "1")
            var zaraShirt2 =
                    ProductModel(17, "Shirt", "142", "1", "Zara", 1, 1, 1, "Cool shirt", "1", "1", "1", "1")
            var zaraShirt3 =
                    ProductModel(18, "Shirt", "201", "1", "Zara", 1, 1, 1, "Amazing shirt", "1", "1", "1", "1")
            var shirts = mutableListOf<ProductModel>(zaraShirt1, zaraShirt2, zaraShirt3)
            var subcategoryModel1 = SubcategoryModel("Shirts", shirts)
            var subcategoryModel3 = SubcategoryModel("Jeans", jeans)
            var subcategories = mutableListOf<SubcategoryModel>(
                    subcategoryModel2,
                    subcategoryModel3,
                    subcategoryModel1
            )
            categoryModel = CategoryModel("Man", subcategories)
        } else {
            var zaraJacket1 =
                    ProductModel(10, "Jacket", "222", "1", "Zara", 1, 1, 1, "Nice jacket", "1", "1", "1", "1")
            var zaraJacket2 =
                    ProductModel(11, "Jacket", "231", "1", "Zara", 1, 1, 1, "Cool jacket", "1", "1", "1", "1")
            var zaraJacket3 =
                    ProductModel(12, "Jacket", "321", "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var jackets = mutableListOf<ProductModel>(zaraJacket1, zaraJacket2, zaraJacket3)
            var subcategoryModel2 =
                    SubcategoryModel("Jeans", mutableListOf(zaraJacket1, zaraJacket2, zaraJacket3))
            var zaraJean1 = ProductModel(13, "Jean", "79", "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraJean2 = ProductModel(14, "Jean", "93", "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraJean3 = ProductModel(15, "Jean", "102", "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var jeans = mutableListOf<ProductModel>(zaraJean1, zaraJean2, zaraJean3)
            var zaraShirt1 = ProductModel(16, "Shirt", "60", "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraShirt2 = ProductModel(17, "Shirt", "142", "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var zaraShirt3 = ProductModel(18, "Shirt", "201", "1", "Zara", 1, 1, 1, "1", "1", "1", "1", "1")
            var shirts = mutableListOf<ProductModel>(zaraShirt1, zaraShirt2, zaraShirt3)
            var subcategoryModel1 = SubcategoryModel("Shirts", shirts)
            var subcategoryModel3 = SubcategoryModel("jeans", jeans)
            var subcategories = mutableListOf<SubcategoryModel>(
                    subcategoryModel2,
                    subcategoryModel3,
                    subcategoryModel1
            )
            categoryModel = CategoryModel("Man", subcategories)

        }
        _displayedCategory.value = categoryModel
    }


}


