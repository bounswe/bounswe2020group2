import './HomePage.less'
import { CategoryBar } from '../CategoryBar'
import getflixLogo from '../../assets/logo.png'
import { useAppContext } from '../../context/AppContext'

export const HomePage = () => {
    // example usage
    const { user, setUser } = useAppContext()

    return (
        <div>
            <HomePage_Splash />
            <CategoryBar />
            <HomePage_MainContent />
        </div>
    )
}

const HomePage_Splash = () => {
    return (
        <div className="splash">
            <img src={getflixLogo} className="splash-logo"></img>
            <div className="splash-slogan">
                <h1>The biggest marketplace in the world</h1>
                <h2>Any product you want, at your doorstep</h2>
            </div>
        </div>
    )
}

const HomePage_MainContent = () => {
    return <div style={{ height: 100 }}></div>
}
