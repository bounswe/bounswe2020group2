import { matchPath, useHistory, useLocation } from 'react-router-dom'
import { SearchInput } from '../SearchInput'
import qs from 'query-string'

export const SearchInputWrapper = () => {
    const history = useHistory()
    const location = useLocation()

    const onSearch = search => {
        history.push({
            pathname: `/search/${search.type}`,
            search:
                '?' +
                qs.stringify({ query: search.query }, { arrayFormat: 'comma', skipNull: true, skipEmptyString: true }),
        })
    }

    const queryParams = qs.parse(location.search, {
        arrayFormat: 'comma',
        parseNumbers: true,
        parseBooleans: true,
    })

    const initialValues = {
        query: queryParams.query,
        type: matchPath(location.pathname, '/search/:type')?.params.type ?? 'products',
    }

    return <SearchInput key={location.pathname} initialValues={initialValues} onSearch={onSearch} />
}
