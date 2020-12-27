import './TrendingGrid.less'
import { Link } from 'react-router-dom'

export const TrendingGrid = ({ trendingProducts = [] }) => {
    return (
        <div className="gallery">
            {trendingProducts.map((product, index) => (
                <figure className={'gallery__item gallery__item--' + index} key={product.id}>
                    <Link to={'/product/' + product.id}>
                        <img src={product.images[0]} className="gallery__img" alt="" />
                    </Link>
                </figure>
            ))}
        </div>
    )
}
