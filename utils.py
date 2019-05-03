import argparse
import logging


def getArgs():
    parser = argparse.ArgumentParser(description="@chowder's AutoClicker")
    parser.add_argument("-s",
                        metavar='S',
                        nargs='+',
                        dest='sleep_times',
                        help='A series of sleep times for the autoclicker',
                        type=int,
                        required=True)
    parser.add_argument("-c",
                        dest='cyclic',
                        help='Flag to indicate infinite repetition',
                        action='store_true',
                        default=False)
    parser.add_argument("-v",
                        dest='verbose',
                        help='Run with verbose logging',
                        action='store_true',
                        default=False)
    parser.add_argument("-g",
                        dest='gaussian',
                        help='Add Gaussian noise to sleep times',
                        action='store_true',
                        default=False)
    return parser.parse_args()
