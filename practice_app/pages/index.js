import { useState } from "react";
import Head from "next/head";

import axios from "axios";

import dynamic from "next/dynamic";

const MapView = dynamic(() => import("../components/MapView"), { ssr: false });
// axios is a library used to make requests

// THIS CODE IS EXECUTED ON THE ********SERVER******** SIDE
// You can use this function to receive the request params
// and render something on the server
// the return of this function is fed into Home component

// REMEMBER: You can always console.log (either in the server or in the browser, depending on where the code is
// executed) to see what each object looks like!
export async function getServerSideProps(ctx) {
  const { req, query } = ctx;
  const { url, method, headers } = req;
  const context = { query, url, method };
  return { props: { context } }; // This object called "context" is the same context object in Home
}

// THIS CODE IS EXECUTED ON THE ********CLIENT******** SIDE
export default function Home({ context }) {
  // <-- This "context" object is the same object return in getServerSideProps
  // REMEMBER: You can always console.log (either in the server or in the browser, depending on where the code is
  // executed) to see what each object looks like!

  const [coordinates, setCoordinates] = useState({
    longitude: 29.046874,
    latitude: 41.085212,
  });

  const [covidStatsString, setCovidStatsString] = useState();

  const [currency, setCurrency] = useState({
    code: "TRY",
    symbol: "TL",
    name: "Turkish Lira",
  });

  const [language, setLanguage] = useState({
    code: "TUR",
    name: "Turkish",
  });

  const [country, setCountry] = useState({
    code: "TR",
    name: "Turkey",
  });

  // To show visually the price conversion process
  const [priceConversionText, setPriceConversionText] = useState(
    "Use the button to try price conversion"
  );

  const onMapClick = async ({ longitude, latitude }) => {
    try {
      console.log({ longitude, latitude });
      const { data } = await axios.get(
        `api/getLocationInfo?lat=${latitude}&lng=${longitude}`
      );
      const { valid, currency, country, language } = data;
      console.log(data);

      if (!valid) {
        setCurrency(undefined);
        setCountry(undefined);
        setLanguage(undefined);
        return;
      }

      setCountry({ name: country });
      setLanguage({ name: language });
      setCurrency(currency);
    } catch (error) {
      console.error(error);
      setCurrency(undefined);
      setCountry(undefined);
    } finally {
      setCoordinates({ longitude, latitude });
    }
  };

  // When the button is clicked, makes a price conversion demonstration.
  const onConvertButtonClick = async () => {
    try {
      const ipdata = await getUsersIP();
      const pricedata = await priceConverter(10, ipdata.query, "USD");
      Promise;
      setPriceConversionText(`Your ip adress is ${
        ipdata.query
      } and currency of the country that this IP belongs to is ${
        pricedata.currency
      }.
            If price of a product is 10 USD, then it is equal to ${pricedata.price.toFixed(
              2
            )} ${pricedata.currency} in your currency.`);
    } catch (error) {
      console.log(error);
      setPriceConversionText("Sorry, service unavailable at this time.");
    }
  };

  // Returns the IP of the user
  const getUsersIP = async () => {
    const { data } = await axios.get("http://ip-api.com/json/");
    return data;
  };

  // Using the given ip, finds the currency of the country that the given IP belongs to.
  // Then, converts the given price in srcCurrency to the new currency.
  // Returns the result
  const priceConverter = async (pr, ip, srcCurrency) => {
    try {
      const { data } = await axios.get(
        "api/getConvertedPrice?ip=" +
          ip +
          "&price=" +
          pr +
          "&srcCurrency=" +
          srcCurrency
      );
      return data;
    } catch (error) {
      console.error(error);
    }
  };
  const getCovidStats = async () => {
    if (country.name == null) {
      setCovidStatsString("Please select a location by clicking on the map");
      return;
    }
    const { data } = await axios.get(
      `api/getStatistics?country=${country.name}`
    );

    if (data.answered == "yes") {
      setCovidStatsString(
        `Country: ${country.name} Death Toll:  ${data.deathToll} Recovered People:  ${data.recovery} Infected People: ${data.infection}`
      );
    } else {
      setCovidStatsString("No information available about your country");
    }
  };

  const locationGreeting = () =>
    `You are a user from ${country.name}, you speak ${language.name} and you buy in ${currency.name}`;

  // variables which are used in axios functions
  var productSetId = 0;
  var productSetDisplayName = "";

  var productId = 0;
  var productDisplayName = "";
  var category = "";
  var description = "";
  var color = "";
  var style = "";

  var updatedId = "";
  var productKey = "";
  var productValue = "";

  var referenceImage = "";

  // onclick method for creating a product set
  async function onCreateProductSetClick() {
    // checks whether all the inputs are given
    if (
      document.getElementById("product-set-title").value === "" ||
      document.getElementById("product-set-display-title").value === ""
    ) {
      alert("please fill set id and name");
      return;
    }

    // saves the input values
    productSetId = document.getElementById("product-set-title").value;
    productSetDisplayName = document.getElementById("product-set-display-title")
      .value;

    // informs the user
    alert("product set has created successfully");

    // sends the input values to getProductSet function by using axios.post
    axios.post("./api/getProductCatalog", {
      id: productSetId,
      name: productSetDisplayName,
      type: "productSet",
    });
  }

  // onclick method for updating a product
  async function onUpdateClick() {
    // checks whether all the inputs are given
    if (
      document.getElementById("updated-title").value === "" ||
      document.getElementById("key-title").value === "" ||
      document.getElementById("value-title").value === ""
    ) {
      alert("please fill product id, key and key value");
      return;
    }

    // saves the input values
    updatedId = document.getElementById("updated-title").value;
    productKey = document.getElementById("key-title").value;
    productValue = document.getElementById("value-title").value;

    // informs the user
    alert("product has updated succesfully");

    // sends the input values to getProductSet function by using axios.patch
    axios.patch("./api/getProductCatalog", {
      id: updatedId,
      key: productKey,
      value: productValue,
    });
  }

  // onclick method for deleting a product
  async function onDeleteProductClick() {
    // checks whether all the input is given
    if (document.getElementById("delete-title").value === "") {
      alert("please fill product id");
      return;
    }

    // saves the input value
    productId = document.getElementById("delete-title").value;

    // informs the user
    alert("product has deleted succesfully");

    // sends the input value to getProdcutSet function by using axios.delete
    axios
      .delete("./api/getProductCatalog", {
        data: {
          type: "product",
          id: productId,
        },
      })
      .then(function (response) {
        resultData = response.data;
        resultStatus = response.status;
        resultStatusText = response.statusText;
      });
  }

  return (
    <div className="container">
      <Head>
        <title>Our example API</title>
        <link rel="icon" href="/favicon.ico" />
        <link
          href="https://api.mapbox.com/mapbox-gl-js/v1.10.0/mapbox-gl.css"
          rel="stylesheet"
        />
        <link
          rel="stylesheet"
          href="https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-geocoder/v4.5.1/mapbox-gl-geocoder.css"
          type="text/css"
        />
      </Head>
      <main>
        <h1>Welcome to our demo website!</h1>
        {priceConversionText && <p>{priceConversionText}</p>}
        <button variant="primary" onClick={onConvertButtonClick}>
          Price Conversion
        </button>
        {country && language && currency && <p>{locationGreeting()} </p>}
        <MapView onMapClick={onMapClick} coordinates={coordinates} />

        <button onClick={getCovidStats}>
          Click me to get Covid death statistics for the country you have chosen
          on the map
        </button>
        {covidStatsString !== undefined && <p>{covidStatsString}</p>}
      </main>
      <Head>
        <title>Getflix</title>
      </Head>
      <h1>Are you vendor?</h1>
      <h2 class="vendor">Do you want to create your own product set?</h2>
      <form>
        <label>
          SetID:
          <input
            type="text"
            placeholder="Add set id"
            id="product-set-title"
            required
          />
        </label>
      </form>
      <form>
        <label>
          SetName:
          <input
            type="text"
            placeholder="Add name"
            id="product-set-display-title"
            required
          />
        </label>
      </form>
      <form>
        <input type="submit" value="Create" onClick={onCreateProductSetClick} />
      </form>
      <h2>Aren't you happy with your product? Try to update its labels!</h2>
      <form>
        <label>
          ProductID:
          <input
            type="text"
            placeholder="Add product id"
            id="updated-title"
            required
          />
        </label>
      </form>
      <form>
        <label>
          Key:
          <input type="text" placeholder="Add key" id="key-title" required />
        </label>
      </form>
      <form>
        <label>
          KeyValue:
          <input
            type="text"
            placeholder="Add value"
            id="value-title"
            required
          />
        </label>
      </form>
      <form>
        <input type="submit" value="Update" onClick={onUpdateClick} />
      </form>
      <h2>Say goodbye to your product!</h2>
      <form>
        <label>
          ProductID:
          <input
            type="text"
            placeholder="Add product id"
            id="delete-title"
            required
          />
        </label>
      </form>
      <form>
        <input type="submit" value="Delete" onClick={onDeleteProductClick} />
      </form>
      <h1>Are you customer?</h1>
      <h2>
        Hmm! There are lots of products in our website! Take a look all of them!
      </h2>
      <form target="_blank" action="/api/getAllProducts" method="GET">
        <input type="submit" value="List" />
      </form>
      <h2>
        Are you interested only in products of your favorite vendor? Really??{" "}
      </h2>
      <form target="_blank" action="/api/getProductsInSet" method="GET">
        <label>
          SetID:
          <input type="text" placeholder="Add set id" name="setId" required />
        </label>
        <input type="submit" value="List" />
      </form>
      <h2 class="special">
        Let's play a game! You give us an image of a product, we give our
        products sorted by similarity percentages!
      </h2>
      <form target="_blank" action="/api/getSimilarProducts" method="GET">
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
          FilePath:
          <input
            type="text"
            placeholder="Add path of file"
            name="filePath"
            required
          />
        </label>
        <label>
          Filter:
          <input
            type="text"
            placeholder="Add path of file"
            name="filter"
            required
          />
        </label>
        <input type="submit" value="List" />
      </form>

      <style jsx global>
        {`
          html,
          body {
            padding: 0;
            margin: 1%;
            font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto,
              Oxygen, Ubuntu, Cantarell, Fira Sans, Droid Sans, Helvetica Neue,
              sans-serif;
          }
          h1 {
            color: #f0e68c;
            background-color: black;
          }
          .special {
            color: brown;
            font-size: 175%;
          }
          * {
            box-sizing: border-box;
          }
        `}
      </style>
    </div>
  );
}
