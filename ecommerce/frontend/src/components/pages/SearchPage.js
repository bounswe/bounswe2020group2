import './SearchPage.less'

import { SearchInput } from '../SearchInput'
import { SearchSidePanel } from '../search/SearchSidePanel'
import { SearchResults } from '../search/SearchResults'

export const SearchPage = () => {
    const products = [...Array(10)].map((x, ix) => {
        return {
            title: 'Title',
            rating: '5',
            price: '30.00',
            currency: 'TL',
            imageUrl: `https://picsum.photos/300?q=${ix}`,
            width: 300,
            productId: ix,
        }
    })

    return (
        <div className={'search-page'}>
            <div className="search-page-bar">
                <SearchInput />
            </div>
            <div className="search-page-main">
                <div className="search-page-side-panel">
                    <SearchSidePanel />
                </div>
                <div className="search-page-results">
                    <SearchResults products={products} />
                </div>
            </div>
        </div>
    )
}
