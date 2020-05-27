import React, { useState } from "react";
import axios from "axios";

const CSS_COLOR_NAMES = [
    "AliceBlue",
    "AntiqueWhite",
    "Aqua",
    "Aquamarine",
    "Azure",
    "Beige",
    "Bisque",
    "Black",
    "BlanchedAlmond",
    "Blue",
    "BlueViolet",
    "Brown"
]    


function getFormValues(event) {
  const formData = new FormData(event.target);
  event.preventDefault();

  const inputs = {};

  for (let [key, value] of formData.entries()) {
    inputs[key] = value;
  }

  return inputs;
}

export default function SimilarProduct({}) {
  const [similarProducts, setSimilarProducts] = useState(undefined);
  const [allProducts, setAllProducts] = useState(undefined);

  const onCreateProductSet = async (event) => {
    const inputs = getFormValues(event);

    // checks whether all the inputs are given
    if (
      inputs.product_set_title === "" ||
      inputs.product_set_display_title === ""
    ) {
      alert("please fill set id and name");
      return;
    }

    // sends the input values to getProductSet function by using axios.post
    axios.post("./api/getProductCatalog", {
      id: inputs.product_set_title,
      name: inputs.product_set_display_title,
      type: "productSet",
    });

    // informs the user
    alert("product set has created successfully");
  };
  const toBase64 = (file) =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });

  const onGetSimilarProducts = async (event) => {
    const { setId, category, file, filter } = getFormValues(event);
    const imageBase64 = await toBase64(file);
    // checks whether the style given by user is valid
    if (
      ![
        "style=women",
        "style=men",
        "style=children",
        "style=home",
        "style=general",
        "stylepackaged",
      ].includes(filter)
    ) {
      alert(
        "Invalid style name. Valid ones are: women, men, children, home, general and packaged"
      );
      return;
    }
    const { data } = await axios.post("./api/getSimilarProducts", {
      setId,
      category,
      imageBase64,
      filter,
    });
    setSimilarProducts(data);
  };

  // onclick method for adding a product to a product set
  const onCreateProduct = async (event) => {
    const inputs = getFormValues(event);
    // checks whether all the inputs are given
    if (
      inputs.product_title === "" ||
      inputs.product_display_title === "" ||
      inputs.category_title === "" ||
      inputs.product_set_id_title === "" ||
      inputs.description_title === "" ||
      inputs.color_title === "" ||
      inputs.style_title === ""
    ) {
      alert(
        "please fill product set id, product id, name, category, description, color and style"
      );
      return;
    }

    // checks whether the category given by user is valid
    if (
      ![
        "apparel",
        "homegoods",
        "toys",
        "apparel-v2",
        "homegoods-v2",
        "toys-v2",
        "packagedgoods-v1",
      ].includes(inputs.category_title)
    ) {
      alert(
        "Invalid category name. Valid ones are: apparel, homegoods, toys, apparel-v2, homegoods-v2, toys-v2 and packagedgood-v1"
      );
      return;
    }
    if (
      !CSS_COLOR_NAMES.map((v) => v.toLowerCase()).includes(
        inputs.color_title.toLowerCase()
      )
    ) {
      alert("The color name must be one of the CSS color.");
      return;
    }

    // checks whether the style given by user is valid
    if (
      !["women", "men", "children", "home", "general", "packaged"].includes(
        inputs.style_title
      )
    ) {
      alert(
        "Invalid style name. Valid ones are: women, men, children, home, general and packaged"
      );
      return;
    }

    // sends the input values to getProductSet function by using axios.post
    axios.post("./api/getProductCatalog", {
      id: inputs.product_title,
      name: inputs.product_display_title,
      category: inputs.category_title,
      type: "product",
      setId: inputs.product_set_id_title,
      description: inputs.description_title,
      color: inputs.color_title,
      style: inputs.style_title,
    });
    // informs the user
    alert("Product has added succesfully");
  };

  return (
    <div className="container">
      <h1>Are you vendor?</h1>
      <h2 class="vendor">Do you want to create your own product set?</h2>
      <form onSubmit={onCreateProductSet}>
        <label>
          SetID:
          <input
            type="text"
            placeholder="Add set id"
            name="product_set_title"
            required
          />
        </label>
        <label>
          SetName:
          <input
            type="text"
            placeholder="Add name"
            name="product_set_display_title"
            required
          />
        </label>
        <input type="submit" value="Create" />
      </form>

      <h2 class="vendor">
        Do you want to add your product into your product set?
      </h2>
      <form onSubmit={onCreateProduct}>
        <label>
          ProductID:
          <input
            type="text"
            placeholder="Add product id"
            name="product_title"
            required
          />
        </label>
        <label>
          SetID:
          <input
            type="text"
            placeholder="Add set id"
            name="product_set_id_title"
            required
          />
        </label>
        <label>
          ProductName:
          <input
            type="text"
            placeholder="Add name"
            name="product_display_title"
            required
          />
        </label>
        <label>
          Category:
          <input
            type="text"
            placeholder="Add category"
            name="category_title"
            required
          />
        </label>
        <label>
          Description:
          <input
            type="text"
            placeholder="Write description"
            name="description_title"
            required
          />
        </label>
        <label>
          Color:
          <input
            type="text"
            placeholder="Write color"
            name="color_title"
            required
          />
        </label>
        <label>
          Style:
          <input
            type="text"
            placeholder="Write style"
            name="style_title"
            required
          />
        </label>
        <input type="submit" value="Add" />
      </form>
      <form onSubmit={onGetSimilarProducts}>
        <label>
          ProductSetID:
          <input
            type="text"
            placeholder="Add product set id"
            name="setId"
            required
          />
        </label>
        <label>
          Category:
          <input
            type="text"
            placeholder="Add category"
            name="category"
            required
          />
        </label>
        <label>
          File:
          <input
            type="file"
            placeholder="Add path of file"
            name="file"
            required
          />
        </label>
        <label>
          Filter:
          <input type="text" placeholder="Add filter" name="filter" required />
        </label>
        <input type="submit" value="List" />
      </form>
      {similarProducts &&
        similarProducts.map((similarProduct) => {
          return (
            <ul key={similarProduct.id}>
              <li>Product id: {similarProduct.id} </li>
              <li>Product name: {similarProduct.name} </li>
              <li>Product description: {similarProduct.description} </li>
              <li>Product category: {similarProduct.category} </li>
              <li>Product similarity score: {similarProduct.score} </li>
            </ul>
          );
        })}
    </div>
  );
}
