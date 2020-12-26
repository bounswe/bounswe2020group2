import { Link } from 'react-router-dom'
import './Purchase.less'

export const Purchase = ({ purchase }) => {
    const { product, status } = purchase

    return (
        <div className="purchase-container">
            <div className="purchase-header">
                <div>header 1</div>
                <div></div>
                <div></div>
            </div>
            <div className="purchase-product">
                <div className="purchase-product-picture">
                    <Link to={`/product/${product.id}`}>
                        <img alt={product.name} width={'100%'} src={product.images[0]}></img>
                    </Link>
                </div>
                <div className="purchase-product-details">
                    <div className="purchase-product-details-title">
                        <Link to={`/product/${product.id}`}>{product.name}</Link>
                    </div>
                </div>
                <div className="purchase-product-status">{status}</div>
                <div className="purchase-product-extra"></div>
            </div>
        </div>
    )
}
