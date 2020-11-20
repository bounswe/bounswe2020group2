import './HomePage.less'
import { CategoryBar } from '../CategoryBar'
import { TrendingGrid } from '../TrendingGrid'
import getflixLogo from '../../assets/logo.png'
import { useAppContext } from '../../context/AppContext'

export const HomePage = () => {
    // example usage
    const { user, setUser } = useAppContext()

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
            <div className="splash-slogan">
                <h1>The biggest marketplace in the world</h1>
                <h2>Any product you want, at your doorstep</h2>
            </div>
        </div>
    )
}

const HomePage_MainContent = () => {
    const products = [
        {
            title: 'Purse',
            rating: '5',
            price: '30.00',
            currency: 'TL',
            imageUrl: 'https://stylecaster.com/wp-content/uploads/2018/10/bag-storage.jpg',
            width: 300,
            productId: 1324,
        },
        {
            title: 'Watch',
            rating: '5',
            price: '45.00',
            currency: 'TL',
            imageUrl: 'https://cdn.pixabay.com/photo/2019/07/13/13/42/watch-4334815_960_720.jpg',
            width: 300,
            productId: 2123,
        },
        {
            title: 'onlar iyi',
            rating: '5',
            price: '30.00',
            currency: 'TL',
            imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/6/65/Product_Photography.jpg',
            width: 300,
            productId: 32345,
        },
        {
            title: 'fena biji',
            rating: '5',
            price: '45.00',
            currency: 'TL',
            imageUrl:
                'https://images.unsplash.com/photo-1526947425960-945c6e72858f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80',
            width: 300,
            productId: 4435,
        },
        {
            title: 'onlar iyi',
            rating: '5',
            price: '96.00',
            currency: 'TL',
            imageUrl:
                'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQwCP24CO6WEGAUhkfVg4ozqfeve3pvkhEEZg&usqp=CAU',
            width: 300,
            productId: 5678,
        },
    ]

    console.log(products)
    return (
        <div className="trending-grid-wrapper">
            <TrendingGrid trendingProducts={products} />
        </div>
    )
}
