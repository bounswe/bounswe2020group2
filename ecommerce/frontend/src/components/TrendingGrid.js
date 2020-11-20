import './TrendingGrid.less'
import { Link } from 'react-router-dom'

export const TrendingGrid = trendingProducts => {
    var gallerytItemNumber = 0
    function gallerytItemNo() {
        gallerytItemNumber += 1
        return gallerytItemNumber
    }
    const products = trendingProducts.trendingProducts
    console.log(products)

    return (
        <div className="gallery">
            {products.map(product => (
                <figure className={'gallery__item gallery__item--'.concat(gallerytItemNo())}>
                    <Link to={'/product/' + product.productId}>
                        <img src={product.imageUrl} className="gallery__img" alt="" />
                    </Link>
                </figure>
            ))}
        </div>
    )
}
