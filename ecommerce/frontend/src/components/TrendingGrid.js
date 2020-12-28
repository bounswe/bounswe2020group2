import './TrendingGrid.less'
import { Link } from 'react-router-dom'
import { Rate } from 'antd'

export const TrendingGrid = ({ trendingProducts = [] }) => {
    console.log('tremd', trendingProducts)
    return (
        <div className="gallery">
            {trendingProducts.map((product, index) => (
                <figure className={'gallery__item gallery__item--' + index} key={product.id}>
                    <Link to={'/product/' + product.id}>
                        <img src={product.images[0]} className="gallery__img" alt="" />
                        <div className="product-info">
                            <div className="product-name">{product.name}</div>
                            <div className="product-price">
                                {product.price}&nbsp;{product.currency}
                            </div>
                            <Rate disabled allowHalf defaultValue={product.rating}></Rate>
                        </div>
                    </Link>
                </figure>
            ))}
        </div>
    )
}
