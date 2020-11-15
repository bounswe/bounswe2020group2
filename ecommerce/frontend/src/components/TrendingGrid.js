import './TrendingGrid.less'

export const TrendingGrid = ({ imageUrls = [] }) => {
    return (
        <div className="gallery">
            <figure className="gallery__item gallery__item--1">
                <img src={imageUrls[0]} className="gallery__img" alt="Image 1" />
            </figure>
            <figure className="gallery__item gallery__item--2">
                <img src={imageUrls[1]} className="gallery__img" alt="Image 2" />
            </figure>
            <figure className="gallery__item gallery__item--3">
                <img src={imageUrls[2]} className="gallery__img" alt="Image 3" />
            </figure>
            <figure className="gallery__item gallery__item--4">
                <img src={imageUrls[3]} className="gallery__img" alt="Image 4" />
            </figure>
            <figure className="gallery__item gallery__item--5">
                <img src={imageUrls[4]} className="gallery__img" alt="Image 5" />
            </figure>
        </div>
    )
}
