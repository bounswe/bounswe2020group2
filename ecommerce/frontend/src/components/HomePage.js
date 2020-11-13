import { CategoryBar } from './CategoryBar'
import getflixLogo from '../assets/logo.png'

export const HomePage = () => {
    const categories = [
        { key: 'electronics', title: 'Electronics' },
        { key: 'home_and_garden', title: 'Home & Garden' },
        { key: 'personal_care', title: 'Personal Care' },
        { key: 'furniture', title: 'Furniture' },
        { key: 'pet_supplies', title: 'Pet Supplies' },
        { key: 'mens_fashion', title: "Men's Fashion" },
        { key: 'womens_fashion', title: "Women's Fashion" },
    ]
    return (
        <div>
            <HomePage_Splash />
            <CategoryBar categories={categories} />
            <HomePage_MainContent />
        </div>
    )
}

const HomePage_Splash = () => {
    return (
        <div className="splash">
            <img src={getflixLogo} className="splash-logo"></img>
            <div className="splash__slogan">
                <h1>The biggest marketplace in the world</h1>
                <h2>Any product you want, at your doorstep</h2>
            </div>
        </div>
    )
}

const HomePage_MainContent = () => {
    return <div style={{ height: 100 }}></div>
}
