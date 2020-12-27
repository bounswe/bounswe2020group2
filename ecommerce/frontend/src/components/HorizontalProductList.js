export const HorizontalProductList = ({filters}) => {
    const fetch = async () => {

    }

    return <div className="product-page-best-sellers">
        <div className="best-sellers-topic">
            Best Sellers in {product?.subcategory.name}
        </div>
        <div className="best-sellers">
            {bestSellers.map(product => (
                <ProductCard key={product.id} product={product} />
            ))}
        </div>
</div>
}