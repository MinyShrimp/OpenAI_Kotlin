import {html, LitElement} from 'lit'
import {customElement} from 'lit/decorators.js'

@customElement('index-element')
export class IndexElement extends LitElement {
    render() {
        return html`
            <div class="container">
            </div>
        `
    }
}
