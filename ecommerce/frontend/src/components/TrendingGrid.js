import './TrendingGrid.less'
import { Link } from 'react-router-dom'

export const TrendingGrid = ({ trendingProducts }) => {
    return (
        <div className="gallery">
            {trendingProducts.map((product, index) => (
                <figure className={'gallery__item gallery__item--' + index} key={product.productId}>
                    <Link to={'/product/' + product.productId}>
                        <img src={product.imageUrl} className="gallery__img" alt="" />
                    </Link>
                </figure>
            ))}
        </div>
    )
}
