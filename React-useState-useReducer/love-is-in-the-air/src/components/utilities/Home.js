import Image from 'react-bootstrap/Image'
import Card from 'react-bootstrap/Card'

function Home() {

    return (
        <div className="text-center">
            <h1 className="display-1 mt-1">Love is in the Air!</h1>
            <Image fluid className="mt-1 mb-4" src={process.env.PUBLIC_URL + "/images/bubble-gum-woman-shoots-an-arrow-with-a-heart-from-a-bow.png"} alt="A woman dressed as a cupid with love's arrow" />
            <Card className="home-card card-scalloped">
                <Card.Body className="home-card-body">
                    <Card.Text>
                        A place to save some of our favourite<br />funny valentines, tender sentiments,<br />or rebukes of all things romance.
                    </Card.Text>
                </Card.Body>
            </Card>
        </div>
    );
}

export default Home;