import { NewsEndpoint} from "Frontend/generated/endpoints";

export async function fetchActiveNews() {
    return await NewsEndpoint.getActiveNews();
}
