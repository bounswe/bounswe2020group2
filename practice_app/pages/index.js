import { useState } from 'react'
import Head from 'next/head'

// axios is a library used to make requests
import axios from 'axios'

// THIS CODE IS EXECUTED ON THE ********SERVER******** SIDE
// You can use this function to receive the request params
// and render something on the server
// the return of this function is fed into Home component

// REMEMBER: You can always console.log (either in the server or in the browser, depending on where the code is
// executed) to see what each object looks like!
export async function getServerSideProps(ctx) {
  const { req, query } = ctx
  const { url, method, headers } = req
  const context = { query, url, method }
  return { props: { context } } // This object called "context" is the same context object in Home
}

// THIS CODE IS EXECUTED ON THE ********CLIENT******** SIDE
export default function Home({ context }) {
  // <-- This "context" object is the same object return in getServerSideProps
  // REMEMBER: You can always console.log (either in the server or in the browser, depending on where the code is
  // executed) to see what each object looks like!

  // this contains the number
  // to know more look at React Hooks documentation
  const [number, setNumber] = useState()

  // this function is called when the button is clicked
  const onClick = async () => {
    // use axios to get a random number from OUR api at http://......./api/getRandomNumber
    // the route is written in /pages/api/getRandomNumber.js
    const { data } = await axios.get('api/getRandomNumber')
    // data contains the number, we set the number using setNumber
    setNumber(data)
  }

  return (
    <div className="container">
      <Head>
        <title>Our example API</title>
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <main>
        Main content
        <button onClick={onClick}>Click me to generate a random number</button>
        {number !== undefined && <p>{number}</p>}
      </main>
      <footer>CMPE 352 - Group 2 - 2020</footer>
      // These are default styles, you can always change them
      <style jsx>
        {`
          .container {
            min-height: 100vh;
            padding: 0 0.5rem;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
          }

          main {
            padding: 5rem 0;
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
          }

          footer {
            width: 100%;
            height: 100px;
            border-top: 1px solid #eaeaea;
            display: flex;
            justify-content: center;
            align-items: center;
          }
        `}
      </style>
      <style jsx global>
        {`
          html,
          body {
            padding: 0;
            margin: 0;
            font-family: -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell, Fira Sans,
              Droid Sans, Helvetica Neue, sans-serif;
          }

          * {
            box-sizing: border-box;
          }
        `}
      </style>
    </div>
  )
}
