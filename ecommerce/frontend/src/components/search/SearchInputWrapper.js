import { useHistory } from 'react-router-dom'
import { SearchInput } from '../SearchInput'
import qs from 'query-string'

export const SearchInputWrapper = () => {
    const history = useHistory()

    const onSearch = search => {
        history.push({
            pathname: `/search/${search.type}`,
            search:
                '?' +
                qs.stringify({ query: search.query }, { arrayFormat: 'comma', skipNull: true, skipEmptyString: true }),
        })
    }

    return <SearchInput initialValues={{}} onSearch={onSearch} />
}
