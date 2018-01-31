import React, {Component} from 'react';

class App extends Component {


    constructor(){
        super()
        this.state = {
            products: []
        }
    }

    componentDidMount() {
        fetch("http://localhost:8080/REPLACE_WITH_CONTEXT_APPLICATION/products")
            .then(response => response.json())
            .then(products => this.setState({products}))
    }

    render() {
        return (
            <div className="App">

                <ul>
                    {
                        this.state.products.map(product => <li key={product.name}>{product.name}</li>)
                    }
                </ul>
            </div>
        );
    }
}

export default App;
