import './TrendingGrid.less'
import { Link } from 'react-router-dom'

export const TrendingGrid = ({ trendingProducts }) => {
    return (
        <div className="gallery">
            {trendingProducts.map((product, index) => (
                <figure className={'gallery__item gallery__item--' + index} key={product.id}>
                    <Link to={'/product/' + product.id}>
                        <img src={product.imageUrl} className="gallery__img" alt="" />
                        <div class="product-info">
                            <div class="product-name">{product.title}</div>
                            <div class="product-price">
                                {product.price}&nbsp;{product.currency}
                            </div>
                        </div>
                    </Link>
                </figure>
            ))}
        </div>
    )
}
